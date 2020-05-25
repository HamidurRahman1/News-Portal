package com.hamidur.ss.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RESTController
{
    @GetMapping(value = "/public/users", produces = MediaType.TEXT_HTML_VALUE)
    public String totalUsers()
    {
        return "<h1>Public API endpoint returning users=125</h1>";
    }

    @GetMapping(value = "/public/revenue", produces = MediaType.TEXT_HTML_VALUE)
    public String totalRevenue()
    {
        return "<h1>Another Public API endpoint returning company's revenue</h1>";
    }
}
