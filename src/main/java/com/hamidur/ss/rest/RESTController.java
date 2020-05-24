package com.hamidur.ss.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RESTController
{
    @GetMapping(value = "/public/totalUsers")
    public String home()
    {
        return "<h1>Public API endpoint displaying totalUser=20130</h1>";
    }
}
