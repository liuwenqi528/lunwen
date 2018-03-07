package com.khcm.user.service.impl.sms;

import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.khcm.user.common.enums.BusinessType;
import com.khcm.user.common.enums.SmsCodeType;
import com.khcm.user.dao.entity.business.sms.SmsCode;
import com.khcm.user.dao.entity.business.sms.SmsLog;
import com.khcm.user.dao.entity.business.system.Template;
import com.khcm.user.dao.repository.master.sms.SmsCodeRepository;
import com.khcm.user.dao.repository.master.sms.SmsLogRepository;
import com.khcm.user.dao.repository.master.sms.SmsTplRepository;
import com.khcm.user.service.api.sms.SmsService;
import com.khcm.user.service.dto.business.sms.SmsCodeDTO;
import com.khcm.user.service.mapper.sms.SmsCodeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    private static final int SMS_TYPE_NORMAL = 0;
    private static final String NATION_CHINA = "86";

    //验证码时间120秒
    private static int EXPIREDTIME = 120;

    @Autowired
    private SmsSingleSender singleSender;

    @Autowired
    private SmsMultiSender multiSender;

    @Autowired
    private SmsCodeRepository smsCodeRepository;

    @Autowired
    private SmsTplRepository smsTplRepository;

    @Autowired
    private SmsLogRepository smsLogRepository;

    @Async
    @Override
    public void sendMessage(String phone, String content) {
        try {
            //1、 发送短信
            SmsSingleSenderResult result = singleSender.send(SMS_TYPE_NORMAL
                    , NATION_CHINA
                    , phone
                    , content
                    , ""
                    , "");
            log.info("sms result: {}", result);

            //2、 保存记录
            SmsLog smsLog = new SmsLog();
            smsLog.setPhone(phone);
            smsLog.setContent(content);
            smsLog.setStatus(result.result);
            smsLog.setRemarks(result.toString());
            smsLogRepository.save(smsLog);
        } catch (Exception ex) {
            log.error("send sms error: " + ex.getMessage(), ex);
        }
    }

    @Override
    public int sendSmsCode(String phone, BusinessType businessType) {
        int templetId = getTempletId(businessType);
        ArrayList<String> paramList = new ArrayList<>();
        int sum = getRandNum(1, 999999);
        paramList.add(Integer.toString(sum));
        paramList.add(Integer.toString(EXPIREDTIME / 60));
        //单发
        try {
            SmsSingleSenderResult smsSingleSenderResult = singleSender.sendWithParam(NATION_CHINA, phone, templetId, paramList, "", "", "");
            if (smsSingleSenderResult.result == 0) {
                //存储入库；
                SmsCode smsCode = buileSmsCode(Integer.toString(sum), phone, SmsCodeType.CHANGEPWD);
                SmsCode smsCode1 = smsCodeRepository.save(smsCode);
                return 0;
            } else {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public SmsCodeDTO validateSmsCode(String phone, String smsCode) {
        SmsCode smsCodeObject = smsCodeRepository.findByPhoneEqualsAndCodeEquals(phone, smsCode);
        return SmsCodeMapper.MAPPER.entityToDTO(smsCodeObject);
    }

    public SmsCode buileSmsCode(String code, String phone, SmsCodeType smsCodeType) {
        SmsCode smsCode = new SmsCode();
        smsCode.setCode(code);
        smsCode.setPhone(phone);
        smsCode.setType(smsCodeType);
        Date date = new Date();
        date.setTime(date.getTime() + EXPIREDTIME * 1000);
        smsCode.setExpiredTime(date);
        return smsCode;
    }

    public int getTempletId(BusinessType businessType) {
        int templetId = 82104;
        //查询腾讯云的短信模板Id;
        Template smsTpl = smsTplRepository.findByBusinessTypeEquals(businessType);
        templetId = smsTpl.getTplId();
        return templetId;
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
}
