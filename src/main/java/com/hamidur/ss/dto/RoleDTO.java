package com.hamidur.ss.dto;

import java.io.Serializable;

public class RoleDTO implements Serializable
{
    private Integer roleId;
    private String role;

    public RoleDTO() {
    }

    public RoleDTO(Integer roleId, String role) {
        this.roleId = roleId;
        this.role = role;
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

    @Override
    public String toString() {
        return "RoleDTO{" +
                "roleId=" + roleId +
                ", role='" + role + '\'' +
                '}';
    }
}
