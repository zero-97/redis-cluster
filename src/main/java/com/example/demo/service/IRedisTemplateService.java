package com.example.demo.service;

public interface IRedisTemplateService {

    void set(String key, String value);

    String get(String key);

    Boolean login(String id);

    Boolean loginDay(String id, String date);

    Boolean loginToday(String id);
}
