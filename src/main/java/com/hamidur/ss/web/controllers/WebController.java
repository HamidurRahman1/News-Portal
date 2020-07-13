package com.hamidur.ss.web.controllers;

import com.hamidur.ss.dto.LoginDTO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WebController
{
    @GetMapping(value = {"/", "/index.html", "/home"})
    public String index(Model model)
    {
        model.addAttribute("login", new LoginDTO());
        return "index";
    }

    @PostMapping(value = "/user-dash")
    public String login(@ModelAttribute("login") LoginDTO loginDTO, Model model)
    {
        System.out.println(loginDTO);
        model.addAttribute("firstname", "Demo User");
        return "htmls/user-dashboard";
    }
}
