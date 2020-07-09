package com.hamidur.ss.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class LoginDTO implements Serializable
{
    @NotNull(message = "username(email) cannot be null")
    @NotBlank(message = "username(email) cannot be empty")
    @Size(min = 5, max = 50, message = "username(email) must be in length of 5-60 characters")
    private String username;

    @NotNull(message = "password cannot be null")
    @NotBlank(message = "password cannot be empty")
    @Size(min = 5, max = 70, message = "password must be in length of 5-15 characters")
    private String password;

    public LoginDTO() {
    }

    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
