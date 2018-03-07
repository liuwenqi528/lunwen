package com.khcm.user.service.impl.log;

import com.khcm.user.dao.entity.business.log.Log;
import com.khcm.user.dao.entity.business.log.QLog;
import com.khcm.user.dao.entity.business.system.App;
import com.khcm.user.dao.entity.business.system.User;
import com.khcm.user.dao.repository.master.log.LogRepository;
import com.khcm.user.dao.repository.master.system.AppRepository;
import com.khcm.user.dao.repository.master.system.UserRepository;
import com.khcm.user.service.api.log.LogService;
import com.khcm.user.service.dto.base.PageDTO;
import com.khcm.user.service.dto.business.log.LogDTO;
import com.khcm.user.service.mapper.log.LogMapper;
import com.khcm.user.service.param.business.log.LogParam;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@Slf4j
public class LogServiceImpl implements LogService {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppRepository appRepository;

    @Async
    @Override
    public void createLoginSuccessLog(String username, String appCode, String content, String ip) {
        User user = userRepository.findByUsernameIs(username);
        App app = appRepository.findByCodeIs(appCode);

        save(user, app, "登录成功", content, ip, Log.LogType.LOGIN_SUCCESS);
    }

    @Async
    @Override
    public void createLoginFailureLog(String username, String appCode, String content, String ip) {
        User user = userRepository.findByUsernameIs(username);
        App app = appRepository.findByCodeIs(appCode);
        save(user, app, "登录失败", content, ip, Log.LogType.LOGIN_FAILURE);
    }

    @Async
    @Override
    public void createOperationLog(Integer userId, String appCode, String title, String content, String ip) {
        User user = userRepository.findOne(userId);
        App app = appRepository.findByCodeIs(appCode);
        save(user, app, title, content, ip, Log.LogType.OPERATION);
    }

    @Override
    public List<LogDTO> getSuccessLogList(LogParam logParam) {
        return null;
    }

    @Override
    public List<LogDTO> getFailureLogList() {
        return null;
    }

    @Override
    public List<LogDTO> getOperationLogList() {
        return null;
    }

    private void save(User user, App app, String title, String content, String ip, Log.LogType logType) {
        Log log = new Log();
        log.setUser(user);
        log.setApp(app);
        log.setTitle(title);
        log.setContent(content);
        log.setIp(ip);
        log.setLogType(logType);

        logRepository.save(log);
    }
    @Override
    public PageDTO<LogDTO> getPage(LogParam logParam) {
        Date beginTime = logParam.getBeginTime();
        Date endTime = logParam.getEndTime();
        String username = logParam.getUsername();
        //1、构造条件
        QLog qLog = QLog.log;
        Log log = LogMapper.MAPPER.paramToEntity(logParam);
        BooleanExpression predicate = qLog.logType.eq(log.getLogType());
        if(Objects.nonNull(beginTime) && Objects.nonNull(endTime)){
           predicate = predicate.and(qLog.gmtCreate.between(beginTime,endTime));
        }else if(Objects.nonNull(beginTime)){
            predicate = predicate.and(qLog.gmtCreate.eq(beginTime));
        }else if(Objects.nonNull(endTime)){
            predicate = predicate.and(qLog.gmtCreate.eq(endTime));
        }
        if(StringUtils.isNotBlank(username)){
            predicate = predicate.and(qLog.user.username.like("%"+username+"%"));
        }
        //2、查询
        Page<Log> page = logRepository.findPage(predicate);

        //3、转换
       List<LogDTO> logDTOList = LogMapper.MAPPER.entityToDTO(page.getContent());
        //4、返回
        return PageDTO.of(page.getTotalElements(), logDTOList);
    }

    @Override
    public void remove(List<Integer> ids) {
        ids.stream().forEach(id -> logRepository.delete(id));
    }

}
