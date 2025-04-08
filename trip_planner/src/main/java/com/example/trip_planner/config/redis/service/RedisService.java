package com.example.trip_planner.config.redis.service;

public interface RedisService {
    public void setKeyAndValue(String token, Long memNo);
    public void setKeyAndValue(String token, String memNo, int expireTime);
    public Long getValueByKey(String token);
    public void deleteByKey(String token);
}
