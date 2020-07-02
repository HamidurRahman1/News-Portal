package com.hamidur.ss.auth.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hamidur.ss.dao.models.Author;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @NotNull(message = "first name cannot be null")
    @NotBlank(message = "first name cannot be empty")
    @Size(min = 2, max = 50, message = "first name can only be in length of 2-50 characters")
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotBlank(message = "last name cannot be empty but optionally can be null")
    @Size(min = 2, max = 50, message = "last name can only be in length of 2-50 characters, null allowed")
    @Column(name = "last_name", length = 50)
    private String lastName;

    @NotNull(message = "username(email) cannot be null")
    @NotBlank(message = "username(email) cannot be empty")
    @Size(min = 5, max = 50, message = "username(email) must be in length of 5-60 characters")
    @Column(name = "username", nullable = false, unique = true, updatable = true, length = 60)
    private String username;

    @NotNull(message = "password cannot be null")
    @NotBlank(message = "password cannot be empty")
    @Size(min = 5, max = 70, message = "password must be in length of 5-15 characters")
    @Column(name = "password", nullable = false, updatable = true, length = 70)
    private String password;

    @NotNull(message = "enabled property cannot be null")
    @Column(name = "enabled", nullable = false, updatable = true)
    private boolean enabled;

    @JsonBackReference
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Author author;

    public User() {}

    public User(Integer userId, String username, String password, boolean enabled, Set<Role> roles) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public User addRole(Role role)
    {
        if(this.roles == null)
            this.roles = new LinkedHashSet<>();
        this.roles.add(role);
        role.getUsers().add(this);
        return this;
    }

    public User removeRole(Role role)
    {
        if(this.roles != null)
            this.roles.remove(role);
        role.getUsers().remove(this);
        return this;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getEnabled() == user.getEnabled() &&
                Objects.equals(getUserId(), user.getUserId()) &&
                Objects.equals(getFirstName(), user.getFirstName()) &&
                Objects.equals(getLastName(), user.getLastName()) &&
                Objects.equals(getUsername(), user.getUsername()) &&
                Objects.equals(getPassword(), user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getFirstName(), getLastName(), getUsername(), getPassword(), getEnabled());
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
