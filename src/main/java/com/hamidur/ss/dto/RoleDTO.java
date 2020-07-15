package com.hamidur.ss.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class RoleDTO implements Serializable
{
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer roleId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleDTO)) return false;
        RoleDTO roleDTO = (RoleDTO) o;
        return Objects.equals(getRole(), roleDTO.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRole());
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "roleId=" + roleId +
                ", role='" + role + '\'' +
                '}';
    }
}
