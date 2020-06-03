package com.hamidur.ss.auth.repos;

import com.hamidur.ss.auth.models.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Integer>
{
    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE username = :username")
    User getUserByUsername(@Param("username") String username);
}
