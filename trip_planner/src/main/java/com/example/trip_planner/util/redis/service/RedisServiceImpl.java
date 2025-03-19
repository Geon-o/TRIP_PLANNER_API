package com.example.trip_planner.util.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService{

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void setKeyAndValue(String token, Long memNo) {
        String memMoToString = String.valueOf(memNo);
        ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
        valueOps.set(token, memMoToString);
    }

    @Override
    public Long getValueByKey(String token) {
        ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
        String tempMemNo = valueOps.get(token);
        Long memNo;

        if (tempMemNo == null) {
            memNo = null;
        } else {
            memNo = Long.parseLong(tempMemNo);
        }

        return memNo;
    }

    @Override
    public void deleteByKey(String token) {
        stringRedisTemplate.delete(token);
    }
}
