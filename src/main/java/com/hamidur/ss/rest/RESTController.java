package com.hamidur.ss.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RESTController
{
    @GetMapping(name = "/")
    public String home()
    {
        return "<h1>Welcome to Home Page</h1>";
    }
}
