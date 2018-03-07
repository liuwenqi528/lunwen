package com.khcm.user.service.impl.sms;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.khcm.user.common.enums.BusinessType;
import com.khcm.user.common.enums.TemplateStatus;
import com.khcm.user.common.enums.TempateType;
import com.khcm.user.common.utils.HttpClientUtil;
import com.khcm.user.dao.entity.business.system.QTemplate;
import com.khcm.user.dao.entity.business.system.Template;
import com.khcm.user.dao.repository.master.template.TemplateRepository;
import com.khcm.user.service.api.template.TemplateService;
import com.khcm.user.service.dto.base.PageDTO;
import com.khcm.user.service.dto.business.sms.SmsTplDTO;
import com.khcm.user.service.mapper.sms.SmsTplMapper;
import com.khcm.user.service.param.business.sms.SmsTplParam;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author rqn on 2018/3/2.
 */
@Service("smsTplService")
@Transactional
@Slf4j
public class SmsTplServiceImpl implements TemplateService {
    @Autowired
    private TemplateRepository smsTplRepository;
    private static int appId = 1400066217;
    private static String appKey = "85d48d6d82b096d8b1d7e63218ccc001";

    @Override
    public SmsTplDTO saveOrUpdate(SmsTplParam smsTplParam) {
        if (Objects.isNull(smsTplParam.getId())) {
            try {
                Map<String, Integer> map = buildTplToTencent(smsTplParam);
                if (Objects.nonNull(map)) {
                    smsTplParam.setTplId(map.get("tmpId"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Template smsTpl = SmsTplMapper.MAPPER.paramToEntity(smsTplParam);
            smsTpl.setTemplateStatus(TemplateStatus.CHECKING);
            return SmsTplMapper.MAPPER.entityToDTO(smsTplRepository.save(smsTpl));
        }
        Template smsTpl = smsTplRepository.findOne(smsTplParam.getId());
        smsTplParam.setTplId(smsTpl.getTplId());
        try {
            buildTplToTencent(smsTplParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        smsTpl.setTemplateStatus(TemplateStatus.CHECKING);
        return SmsTplMapper.MAPPER.entityToDTO(smsTplRepository.save(SmsTplMapper.MAPPER.paramToEntity(smsTplParam, smsTpl)));
    }

    /**
     * 创建短信模板到腾讯云
     *
     * @param smsTplParam
     * @return
     * @throws Exception
     */
    public Map<String, Integer> buildTplToTencent(SmsTplParam smsTplParam) throws Exception {
        String result = null;
        if (Objects.isNull(smsTplParam.getTplId())) {
            String url = "https://yun.tim.qq.com/v5/tlssmssvr/add_template";
            result = createReq(url, 1, smsTplParam, null);
        } else {
            String url = "https://yun.tim.qq.com/v5/tlssmssvr/mod_template";
            result = createReq(url, 3, smsTplParam, null);
        }
        JSONObject json = JSONObject.parseObject(result);
        Map<String, Integer> res = null;
        if ("0".equals(json.getString("result"))) {
            res = new HashMap<>();
            JSONObject obj = json.getJSONObject("data");
            res.put("tmpId", obj.getIntValue("id"));
            res.put("tmpStatus", obj.getIntValue("status"));
        }
        return res;
    }

    /**
     * 删除
     *
     * @param idList
     */
    @Override
    public void remove(List<Integer> idList) {
        boolean bool = false;
        List<Integer> tplList = new ArrayList<>();
        try {
            idList.forEach(id -> {
                Template smsTpl = smsTplRepository.getOne(id);
                tplList.add(smsTpl.getTplId());
            });
            bool = delTplToTencent(tplList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bool) {
            idList.forEach(id -> {
                smsTplRepository.delete(id);
            });
        }
    }

    /**
     * 删除腾讯云的短信模板
     *
     * @param idList
     * @return
     * @throws Exception
     */
    public boolean delTplToTencent(List<Integer> idList) throws Exception {
        String url = "https://yun.tim.qq.com/v5/tlssmssvr/del_template";
        String result = createReq(url, 2, null, idList);
        JSONObject json = JSONObject.parseObject(result);
        if (json.get("result").toString().equals("0")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查询修改短信模板状态
     *
     * @param id
     */
    @Override
    public String changeSmsTplTencent(Integer id) {
        String reply = "pass";
        Template smsTpl = smsTplRepository.getOne(id);
        List<Integer> tplIds = new ArrayList<>();
        tplIds.add(smsTpl.getTplId());
        try {
            String url = "https://yun.tim.qq.com/v5/tlssmssvr/get_template";
            String result = createReq(url, 2, null, tplIds);
            JSONObject json = JSONObject.parseObject(result);
            Map<Integer, Integer> resMap = null;
            if (json.get("result").toString().equals("0")) {
                resMap = new HashMap<>();
                JSONArray jsonArray = json.getJSONArray("data");
                for (int i = 0; i < jsonArray.size(); i++) {
                    Map map = jsonArray.getObject(i, Map.class);
                    if (Integer.parseInt(map.get("status").toString()) == 2) {
                        reply = map.get("reply").toString();
                        resMap.put(Integer.parseInt(map.get("id").toString()), Integer.parseInt(map.get("status").toString()));
                        smsTplRepository.updateTplreply(Integer.parseInt(map.get("id").toString()), reply);
                    } else {
                        resMap.put(Integer.parseInt(map.get("id").toString()), Integer.parseInt(map.get("status").toString()));
                    }
                }
            }
            Map<Integer, TemplateStatus> data = new HashMap<>();
            resMap.forEach((k, v) -> {
                if (v == 0) {
                    data.put(k, TemplateStatus.PASSED);
                } else if (v == 1) {
                    data.put(k, TemplateStatus.CHECKING);
                } else {
                    data.put(k, TemplateStatus.REJECTED);
                }
            });
            data.forEach((k, v) -> smsTplRepository.updateTplStatus(k, v));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reply;
    }

    /**
     * 组织请求包
     *
     * @param url
     * @param type
     * @param smsTplParam
     * @param idList
     * @return
     * @throws Exception
     */
    public String createReq(String url, Integer type, SmsTplParam smsTplParam, List<Integer> idList) throws Exception {
        Integer random = getRandNum(1, 999999);
        Long curTime = System.currentTimeMillis() / 1000;
        String sig = new String(strToHash("appkey=" + appKey + "&random=" + random.toString() + "&time=" + String.valueOf(curTime)));
        // 按照协议组织 post 请求包体
        JSONObject data = new JSONObject();
        data.put("sig", sig);
        data.put("time", curTime);
        if (type == 1) {
            data.put("title", smsTplParam.getTplName());
            data.put("type", smsTplParam.getType());
            data.put("text", smsTplParam.getContent());
        } else if (type == 2) {
            data.put("tpl_id", idList.toArray());
        } else {
            data.put("title", smsTplParam.getTplName());
            data.put("type", smsTplParam.getType());
            data.put("text", smsTplParam.getContent());
            data.put("tpl_id", smsTplParam.getTplId());
        }
        String wholeUrl = String.format("%s?sdkappid=%d&random=%d", url, appId, random);
        String result = HttpClientUtil.doPost(wholeUrl, data.toString());
        return result;
    }

    /**
     * 获取min到max之间的随机数
     *
     * @param min
     * @param max
     * @return
     */
    public int getRandNum(int min, int max) {
        int randNum = min + (int) (Math.random() * ((max - min) + 1));
        return randNum;
    }

    /**
     * 生成腾讯云sig 参数值
     *
     * @param str
     * @return
     * @throws NoSuchAlgorithmException
     */
    protected String strToHash(String str) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] inputByteArray = str.getBytes();
        messageDigest.update(inputByteArray);
        byte[] resultByteArray = messageDigest.digest();
        return byteArrayToHex(resultByteArray);
    }

    public String byteArrayToHex(byte[] byteArray) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        return new String(resultCharArray);
    }

    /**
     * 分页查询--可带有参数
     *
     * @param smsTplParam
     * @return
     */
    @Override
    public PageDTO<SmsTplDTO> getPage(SmsTplParam smsTplParam) {
        //1、构造条件
        QTemplate qSmsTpl = QTemplate.template;
        BooleanExpression predicate = qSmsTpl.isNotNull();
        String tplName = smsTplParam.getTplName();
        //短信模板名称不为空时，添加like条件
        if (StringUtils.isNotBlank(tplName)) {
            predicate = qSmsTpl.tplName.like("%" + tplName + "%");
        }

//        String smsType = smsTplParam.getSmsType();
//        if (StringUtils.isNotBlank(smsType)) {
//            //predicate = predicate.and(qSmsTpl.businessType.eq(Enum.valueOf(BusinessType.class, smsType)));
//        }

        //短信类型不为空时，添加eq
        String tmpType = smsTplParam.getTemplateType();
        if (Objects.nonNull(tmpType)) {
            predicate = predicate.and(qSmsTpl.templateType.eq(Enum.valueOf(TempateType.class,tmpType)));
        }
        //短信模板状态不为空时，添加eq
        String smsTplStatus = smsTplParam.getTemplateStatus();
        if (StringUtils.isNotBlank(smsTplStatus)) {
            predicate = predicate.and(qSmsTpl.templateStatus.eq(Enum.valueOf(TemplateStatus.class, smsTplStatus)));
        }
        //短信类型不为空时，添加eq
        Integer smsPlatform = smsTplParam.getPlatform();
        if (Objects.nonNull(smsPlatform)) {
            predicate = predicate.and(qSmsTpl.platform.eq(smsPlatform));
        }
        Optional<Date> optionalBeginDate = Optional.ofNullable(smsTplParam.getBeginTime());
        predicate = predicate.and(optionalBeginDate.map(beginDate -> qSmsTpl.gmtCreate.goe(beginDate)).orElse(null));
        Optional<Date> optionalEndDate = Optional.ofNullable(smsTplParam.getEndTime());
        predicate = predicate.and(optionalEndDate.map(endDate -> qSmsTpl.gmtCreate.loe(endDate)).orElse(null));
        //2、查询
        Page<Template> page = smsTplRepository.findPage(predicate);

        //3、转换
        List<SmsTplDTO> smsTplDTOS = SmsTplMapper.MAPPER.entityToDTO(page.getContent());
        //4、返回
        return PageDTO.of(page.getTotalElements(), smsTplDTOS);
    }

    /**
     * 根据id查询某条数据
     *
     * @param id
     * @return
     */
    @Override
    public SmsTplDTO getById(Integer id) {
        return SmsTplMapper.MAPPER.entityToDTO(smsTplRepository.getOne(id));
    }

    /**
     * 不带有分页的查询-可带参数
     *
     * @param smsTplParam
     * @return
     */
    @Override
    public List<SmsTplDTO> getList(SmsTplParam smsTplParam) {
        //1、构造条件
        QTemplate qSmsTpl = QTemplate.template;
        BooleanExpression predicate = qSmsTpl.isNotNull();

        //短信业务类型不为空时，添加eq
        String smsType = smsTplParam.getBusinessType();
        //短信类型不为空时，添加eq
        Integer type = smsTplParam.getType();
        //短信模板状态不为空时，添加eq
        String smsTplStatus = smsTplParam.getTemplateStatus();
        //短信类型不为空时，添加eq
        Integer smsPlatform = smsTplParam.getPlatform();
        if (StringUtils.isNotBlank(smsType)) {
            predicate = predicate.and(qSmsTpl.businessType.eq(Enum.valueOf(BusinessType.class, smsType)));
        }
//        else if (Objects.nonNull(type)) {
//            predicate = predicate.and(qSmsTpl.type.eq(type));
//        }
        else if (Objects.nonNull(type)) {
            predicate = predicate.and(qSmsTpl.templateStatus.eq(Enum.valueOf(TemplateStatus.class, smsTplStatus)));
        } else if (Objects.nonNull(smsPlatform)) {
            predicate = predicate.and(qSmsTpl.platform.eq(smsPlatform));
        }

        return SmsTplMapper.MAPPER.entityToDTO(smsTplRepository.findList(predicate));
    }

    /**
     * 根据参数查询是否字段值重复
     *
     * @param smsTplParam
     * @return
     */
    @Override
    public SmsTplDTO getOne(SmsTplParam smsTplParam) {
        //1、构造条件
        QTemplate qSmsTpl = QTemplate.template;
        BooleanExpression p = qSmsTpl.isNotNull();

        //2、条件
        if (StringUtils.isNotBlank(smsTplParam.getTplName())) {
            p = qSmsTpl.tplName.eq(smsTplParam.getTplName());
        }
        if (Objects.nonNull(smsTplParam.getId())) {
            p = p.and(qSmsTpl.id.ne(smsTplParam.getId()));
        }
        Template smsTpl = Optional.ofNullable(smsTplRepository.findList(p)).filter(apps -> apps.size() > 0).map(apps -> apps.get(0)).orElse(null);
        return SmsTplMapper.MAPPER.entityToDTO(smsTpl);
    }
}
