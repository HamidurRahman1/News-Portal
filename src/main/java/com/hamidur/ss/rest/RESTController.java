package com.hamidur.ss.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping(value = "/admin", produces = MediaType.TEXT_HTML_VALUE)
    public String admin()
    {
        return "<h1>Displaying information related to an admin after authentication</h1>";
    }

    @GetMapping(value = "/user", produces = MediaType.TEXT_HTML_VALUE)
    public String user()
    {
        return "<h1>Displaying information related to a user after authentication</h1>";
    }

    @GetMapping(value = "/user/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String userById(@PathVariable Integer id)
    {
        return "<h1>Displaying information related to a user="+id+" after authentication</h1>";
    }
}
