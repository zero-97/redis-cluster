package com.example.demo.service.impl;

import cn.hutool.core.date.DateUtil;
import com.example.demo.service.IRedissonService;
import org.redisson.api.RBitSet;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author yuanqizhang
 */
@Service
public class RedissonServiceImpl implements IRedissonService {

    private final String LOGIN_SIGN = "login";

    @Autowired
    @Qualifier("redissonClient")
    private RedissonClient redissonClient;

    @Override
    public boolean lock(String key) {
        // 1.设置分布式锁
        RLock lock = redissonClient.getLock(key);
        // 2.占用锁
        boolean res = false;
        try {
            res = lock.tryLock(0, TimeUnit.SECONDS);
            if (res) {
                try {
                    // 3.执行业务
                    try {
                        Thread.sleep(40000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    // 4.释放锁
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public void set(String key, String value) {
        RBucket<String> bucket = redissonClient.getBucket(key);
        bucket.set(value);
    }

    @Override
    public String get(String key) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        return String.valueOf(bucket.get());
    }

    @Override
    public boolean login(String id) {
        Date now = DateUtil.date();
        String yearMonth = DateUtil.format(now, "yyyyMM");

        String key = LOGIN_SIGN + ":" + id + ":" + yearMonth;
        long offset = DateUtil.dayOfMonth(now) - 1;

        RBitSet bitSet = redissonClient.getBitSet(key);
        return bitSet.set(offset);
    }

    @Override
    public boolean loginDay(String id, String date) {
        Date now = DateUtil.parse(date, "yyyy-MM-dd");
        String yearMonth = DateUtil.format(now, "yyyyMM");

        String key = LOGIN_SIGN+":"+id+":"+yearMonth;
        long offset = DateUtil.dayOfMonth(now)-1;

        RBitSet bitSet = redissonClient.getBitSet(key);
        return bitSet.get(offset);
    }

    @Override
    public boolean loginToday(String id) {
        String today = DateUtil.today();
        return loginDay(id, today);
    }
}
