package com.flexsible.concurrency.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisDistributedLock {

    private final RedisTemplate<String, Object> redisTemplate;

    public Boolean acquireLock(
            String key,
            String value,
            Long timeout) {

        Boolean result = redisTemplate
                .opsForValue()
                .setIfAbsent(key, value, timeout, TimeUnit.SECONDS);

        return result != null && result;
    }

    public void releaseLock(
            String key) {

        redisTemplate.delete(key);
    }
}
