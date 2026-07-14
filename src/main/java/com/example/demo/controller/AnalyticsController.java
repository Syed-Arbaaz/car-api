package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AnalyticsDTO;
import com.example.demo.service.AnalyticsService;

@RestController
@RequestMapping("/analytics")
//@CrossOrigin("*")
public class AnalyticsController {
    
    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping
    public AnalyticsDTO getDashboardStats(){
        return analyticsService.getDashboardStats();
    }
}
