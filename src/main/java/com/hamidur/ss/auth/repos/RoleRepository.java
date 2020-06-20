package com.hamidur.ss.auth.repos;

import com.hamidur.ss.auth.models.Role;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer>
{
    @Query(nativeQuery = true, value =
                    "SELECT * FROM roles r inner join users_roles ur on  r.role_id = ur.role_id" +
                    " and ur.user_id in (select user_id from users where username = :username)")
    Set<Role> getRolesByUsername(@Param("username") String username);

    @Query(nativeQuery = true, value = "SELECT * FROM roles")
    Set<Role> getAll();
}
