package com.khcm.user.service.impl.message;

import com.khcm.user.common.enums.BusinessType;
import com.khcm.user.common.enums.TempateType;
import com.khcm.user.common.enums.TemplateStatus;
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

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author rqn on 2018/3/2.
 */
@Service("messageTplService")
@Transactional
@Slf4j
public class MessageTplServiceImpl implements TemplateService {
    @Autowired
    private TemplateRepository smsTplRepository;
    @Override
    public SmsTplDTO saveOrUpdate(SmsTplParam smsTplParam) {
        if (Objects.isNull(smsTplParam.getId())) {
            Template smsTpl = SmsTplMapper.MAPPER.paramToEntity(smsTplParam);
            return SmsTplMapper.MAPPER.entityToDTO(smsTplRepository.save(smsTpl));
        }
        Template smsTpl = smsTplRepository.findOne(smsTplParam.getId());
        smsTplParam.setTplId(smsTpl.getTplId());
        return SmsTplMapper.MAPPER.entityToDTO(smsTplRepository.save(SmsTplMapper.MAPPER.paramToEntity(smsTplParam, smsTpl)));
    }

    /**
     * 删除
     *
     * @param idList
     */
    @Override
    public void remove(List<Integer> idList) {
        idList.forEach(id -> {
            smsTplRepository.delete(id);
        });
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
        //短信业务类型不为空时，添加eq
//        String smsType = smsTplParam.getSmsType();
//        if (StringUtils.isNotBlank(smsType)) {
//            //predicate = predicate.and(qSmsTpl.businessType.eq(Enum.valueOf(BusinessType.class, smsType)));
//        }


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

    @Override
    public String changeSmsTplTencent(Integer id) {
        return null;
    }
}
