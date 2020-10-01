package com.hamidur.np.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO implements Serializable
{
    private Integer userId;

    @NotNull(message = "first name cannot be null")
    @NotBlank(message = "first name cannot be empty")
    @Size(min = 2, max = 50, message = "first name can only be in length of 2-50 characters")
    private String firstName;

    @NotNull(message = "last name cannot be null")
    @NotBlank(message = "last name cannot be null")
    @Size(min = 2, max = 50, message = "last name can only be in length of 2-50 characters")
    private String lastName;

    @NotNull(message = "username(email) cannot be null")
    @NotBlank(message = "username(email) cannot be empty")
    @Size(min = 5, max = 60, message = "username(email) must be in length of 5-60 characters")
    private String username;

    @NotNull(message = "password cannot be null")
    @NotBlank(message = "password cannot be empty")
    @Size(min = 5, max = 15, message = "password must be in length of 5-15 characters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean enabled;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<RoleDTO> roles;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<ArticleDTO> articles;

    public UserDTO() {
    }

    public UserDTO(Integer userId, String firstName, String lastName, String username, String password, Set<RoleDTO> roles, Set<ArticleDTO> articles) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.articles = articles;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

    public Set<ArticleDTO> getArticles() {
        return articles;
    }

    public void setArticles(Set<ArticleDTO> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", roles=" + roles +
                ", articles=" + articles +
                '}';
    }
}
