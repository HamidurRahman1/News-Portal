package com.hamidur.ss.auth.repos;

import com.hamidur.ss.auth.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>
{
    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE username = :username")
    User getUserByUsername(@Param("username") String username);

    @Query(nativeQuery = true, value = "delete from users_roles where user_id = :u_i and role_id = :r_i")
    @Modifying
    @Transactional
    int revokeRole(@Param("u_i") Integer userId, @Param("r_i") Integer roleId);

    @Query(nativeQuery = true, value = "insert into users_roles (user_id, role_id) values (:u_i, :r_i)")
    @Modifying
    @Transactional
    int addRole(@Param("u_i") Integer userId, @Param("r_i") Integer roleId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "insert into users (username, password, enabled) values (:un, :p, :e);")
    int insertUserEntity(@Param("un") String username, @Param("p") String password, @Param("e") boolean enabled);
}
