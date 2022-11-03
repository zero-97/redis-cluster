package com.example.demo.service.impl;

import cn.hutool.core.date.DateUtil;
import com.example.demo.service.IRedisTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RedisTemplateServiceImpl implements IRedisTemplateService {

    private final String LOGIN_SIGN = "login";

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Boolean login(String id) {
        Date now = DateUtil.date();
        String yearMonth = DateUtil.format(now, "yyyyMM");

        String key = LOGIN_SIGN + ":" + id + ":" + yearMonth;
        long offset = DateUtil.dayOfMonth(now) - 1;

        return redisTemplate.opsForValue().setBit(key, offset, true);
    }

    @Override
    public Boolean loginDay(String id, String date) {
        Date now = DateUtil.parse(date, "yyyy-MM-dd");
        String yearMonth = DateUtil.format(now, "yyyyMM");

        String key = LOGIN_SIGN+":"+id+":"+yearMonth;
        long offset = DateUtil.dayOfMonth(now)-1;

        return redisTemplate.opsForValue().getBit(key, offset);
    }

    @Override
    public Boolean loginToday(String id) {
        String today = DateUtil.today();
        return loginDay(id, today);
    }

}


