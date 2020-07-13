package com.hamidur.ss.web.controllers;

import com.hamidur.ss.auth.models.User;
import com.hamidur.ss.auth.services.AppUserDetails;
import com.hamidur.ss.auth.services.UserService;
import com.hamidur.ss.dto.LoginDTO;
import com.hamidur.ss.dto.UserDTO;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class PublicWebController
{
    private final UserService userService;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final ModelMapper modelMapper;

    public PublicWebController(final UserService userService, final DaoAuthenticationProvider daoAuthenticationProvider,
                               final ModelMapper modelMapper) {
        this.userService = userService;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = {"/", "/index.html", "/home", "/index"})
    public String index(Model model)
    {
        model.addAttribute("login", new LoginDTO());
        model.addAttribute("userSignup", new UserDTO());
        return "index";
    }

    @PostMapping(value = "/user-dash")
    public String login(@ModelAttribute("login") LoginDTO loginDTO, Model model)
    {
        try
        {
            Authentication authentication = daoAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
            User user = ((AppUserDetails)authentication.getPrincipal()).getUser();
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            model.addAttribute("user", userDTO);
            return "views/user-dashboard";
        }
        catch (RuntimeException ex)
        {
            model.addAttribute("errorHeadline", "LoginError");
            model.addAttribute("errorMsg", ex.getMessage());
            return "redirect:errors/error";
        }
    }

    @PostMapping(value = "/s/signup")
    public String signUp(@ModelAttribute("userSignup") UserDTO userDTO, Model model)
    {
        // after successful sign up redirect to activation page
        model.addAttribute("success", true);
        return "views/signup";
    }
}
