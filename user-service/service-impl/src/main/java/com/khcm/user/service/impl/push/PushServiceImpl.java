package com.khcm.user.service.impl.push;

import com.khcm.user.service.api.push.PushService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PushServiceImpl implements PushService {

    @Async
    @Override
    public void pushMessage(String username, String content) {

    }
}
