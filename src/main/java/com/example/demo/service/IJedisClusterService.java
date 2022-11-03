package com.example.demo.service;

/**
 * @author yuanqizhang
 */
public interface IJedisClusterService {

    Boolean login(String id);

    Boolean loginDay(String id, String date);

    Long loginDays(String id, String staDate, String endDate);

    Long loginMonth(String id, String yearMonth);

    Boolean loginToday(String id);
}
