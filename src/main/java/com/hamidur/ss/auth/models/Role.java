package com.hamidur.ss.auth.models;

import java.io.Serializable;

import java.util.Objects;
import java.util.Set;

public class Role implements Serializable
{
    private Integer roleId;
    private String role;
    private Set<User> users;

    public Role() {}

    public Role(Integer roleId, String role, Set<User> users) {
        this.roleId = roleId;
        this.role = role;
        this.users = users;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role1 = (Role) o;
        return Objects.equals(getRoleId(), role1.getRoleId()) &&
                Objects.equals(getRole(), role1.getRole()) &&
                Objects.equals(getUsers().size(), role1.getUsers().size());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoleId(), getRole(), getUsers().hashCode());
    }
}
