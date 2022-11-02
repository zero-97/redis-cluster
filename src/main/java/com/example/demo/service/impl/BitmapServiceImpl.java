package com.example.demo.service.impl;

import cn.hutool.core.date.DateUtil;
import com.example.demo.service.IBitmapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.Date;

/**
 * @author yuanqizhang
 */
@Service
public class BitmapServiceImpl implements IBitmapService {

    private final String LOGIN_SIGN = "login";

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public Boolean login(String id) {
        Date now = DateUtil.date();
        String yearMonth = DateUtil.format(now, "yyyyMM");

        String key = LOGIN_SIGN+":"+id+":"+yearMonth;
        long offset = DateUtil.dayOfMonth(now)-1;

        return jedisCluster.setbit(key, offset, true);
    }

    @Override
    public Boolean loginDay(String id, String date) {
        Date now = DateUtil.parse(date, "yyyy-MM-dd");
        String yearMonth = DateUtil.format(now, "yyyyMM");

        String key = LOGIN_SIGN+":"+id+":"+yearMonth;
        long offset = DateUtil.dayOfMonth(now)-1;

        return jedisCluster.getbit(key, offset);
    }

    @Override
    public Long loginDays(String id, String staDate, String endDate) {
        Date sta = DateUtil.parse(staDate, "yyyy-MM-dd");
        Date end = DateUtil.parse(endDate, "yyyy-MM-dd");
        String yearMonth = DateUtil.format(sta, "yyyyMM");

        String key = LOGIN_SIGN+":"+id+":"+yearMonth;
        long staOffset = DateUtil.dayOfMonth(sta)-1;
        long endOffset = DateUtil.dayOfMonth(end)-1;

        return jedisCluster.bitcount(key, staOffset, endOffset);
    }

    @Override
    public Long loginMonth(String id, String yearMonth) {
        String key = LOGIN_SIGN+":"+id+":"+yearMonth;
        return jedisCluster.bitcount(key);
    }

    @Override
    public Boolean loginToday(String id) {
        String today = DateUtil.today();
        return loginDay(id, today);
    }


}
