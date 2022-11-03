package com.example.demo.service.impl;


import com.example.demo.service.IStringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

@Service
public class StringServiceImpl implements IStringService {

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void set(String key, String value) {
        jedisCluster.set(key, value);
    }

    @Override
    public String get(String key) {
        return jedisCluster.get(key);
    }
}
