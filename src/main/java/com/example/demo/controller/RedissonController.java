package com.example.demo.controller;

import com.example.demo.service.IRedissonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuanqizhang
 */
@RestController
@RequestMapping("/redis/redisson")
public class RedissonController {

    @Autowired
    IRedissonService service;

    /**
     *
     * @param
     */
    @GetMapping("lock")
    public boolean lock(@RequestParam String key){
        return service.lock(key);
    }

    /**
     *
     * @param
     */
    @GetMapping("set")
    public void set(@RequestParam String key, @RequestParam String value){
        service.set(key, value);
    }

    /**
     *
     * @param
     */
    @GetMapping("get")
    public String get(@RequestParam String key){
        return service.get(key);
    }
}
