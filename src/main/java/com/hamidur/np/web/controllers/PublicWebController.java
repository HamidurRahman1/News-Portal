package com.hamidur.np.web.controllers;

import com.hamidur.np.auth.models.User;
import com.hamidur.np.auth.services.AppUserDetails;
import com.hamidur.np.dto.LoginDTO;
import com.hamidur.np.dto.UserDTO;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class PublicWebController
{
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final ModelMapper modelMapper;

    public PublicWebController(final DaoAuthenticationProvider daoAuthenticationProvider, final ModelMapper modelMapper) {
        this.daoAuthenticationProvider = daoAuthenticationProvider;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/user-dash")
    public String login(@ModelAttribute("login") LoginDTO loginDTO, Model model, HttpSession session)
    {
        try
        {
            Authentication authentication = daoAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
            User user = ((AppUserDetails)authentication.getPrincipal()).getUser();
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            model.addAttribute("user", userDTO);
            session.setAttribute("user", userDTO);
            return "views/user-dashboard";
        }
        catch (RuntimeException ex)
        {
            model.addAttribute("errorHeadline", "LoginError");
            model.addAttribute("errorMsg", ex.getMessage());
            return "errors/error";
        }
    }

    @PostMapping(value = "/s/signup")
    public String signUp(@ModelAttribute("userSignup") UserDTO userDTO, Model model)
    {
        model.addAttribute("success", true);
        return "views/signup";
    }
}
