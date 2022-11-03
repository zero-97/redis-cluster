package com.example.demo.controller;

import com.example.demo.service.IJedisClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuanqizhang
 */
@RestController
@RequestMapping("/redis/jedisCluster")
public class JedisClusterController {

    @Autowired
    private IJedisClusterService service;

    /**
     *
     * @param
     */
    @GetMapping("login")
    public boolean login(@RequestParam String id){
        return service.login(id);
    }

    /**
     *
     * @param
     */
    @GetMapping("loginDay")
    public boolean loginDay(@RequestParam String id, @RequestParam String date){
        return service.loginDay(id, date);
    }

    /**
     *
     * @param
     */
    @GetMapping("loginDays")
    public long loginDays(@RequestParam String id, @RequestParam String staDate, @RequestParam String endDate){
        return service.loginDays(id, staDate, endDate);
    }

    /**
     *
     * @param
     */
    @GetMapping("loginMonth")
    public long loginMonth(@RequestParam String id, @RequestParam String yearMonth){
        return service.loginMonth(id, yearMonth);
    }

    /**
     *
     * @param
     */
    @GetMapping("loginToday")
    public boolean loginToday(@RequestParam String id){
        return service.loginToday(id);
    }
}
