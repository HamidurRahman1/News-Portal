package com.hamidur.ss.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class HomeController
{
    @GetMapping(value = "/")
    public String home()
    {
        return "<h1>Home Page. Available to everyone</h1>";
    }

    @GetMapping(value = "/access-denied")
    public String accessDenied()
    {
        return "<h1>Are you trying to sneak in? Nahhh!!! we do not allow that here</h1>";
    }
}
