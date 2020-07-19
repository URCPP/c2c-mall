package com.diandian.admin.merchant.common.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ThreadTaskJob {

    @Autowired
    private RedisTemplate redisTemplate;

    @Async("defaultTaskExecutor")
    public void deleteRedisValue(String key) {
        redisTemplate.delete(key);
    }
}
