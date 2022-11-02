package com.example.demo.service;

/**
 * @author yuanqizhang
 */
public interface IRedissonService {

    boolean lock(String key);

    void set(String key, String value);

    String get(String key);
}
