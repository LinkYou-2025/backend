package com.umc.linkyou.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate redisTemplate;

    // Redis에 값 저장
    public void setValues(String key, String value, Duration duration) {
        redisTemplate.opsForValue().set(key, value, duration);
    }

    // Redis에서 값 가져오기
    public String getValues(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 값 존재 여부 확인
    public boolean checkExistsValue(String value) {
        return value != null && !value.isEmpty();
    }
}