package com.example.demo.controller;

import com.example.demo.service.IStringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis/String")
public class StringController {

    @Autowired
    IStringService service;

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
