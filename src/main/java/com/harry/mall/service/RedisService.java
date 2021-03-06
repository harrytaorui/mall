package com.harry.mall.service;

public interface RedisService {
    void set(String key, String value);

    String get(String key);

    boolean expire(String key, long expireTime);

    void remove(String key);

    Long increment(String key, long delta);
}
