package com.hamidur.ss.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RESTController
{
    @GetMapping(value = "/public/totalUsers", produces = MediaType.TEXT_HTML_VALUE)
    public String home()
    {
        return "<h1>Public API endpoint returning totalUser=125</h1>";
    }
}
