package com.example.demo.controller;

import com.example.demo.service.IBitmapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuanqizhang
 */
@RestController
@RequestMapping("/redis/bitmap")
public class BitmapController {

    @Autowired
    private IBitmapService bitmapService;



}
