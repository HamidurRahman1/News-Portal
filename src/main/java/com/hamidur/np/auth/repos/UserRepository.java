package com.hamidur.np.auth.repos;

import com.hamidur.np.auth.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>
{
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value =
                    "insert into users (first_name, last_name, username, password, enabled) values ((:fn), (:ln), (:un), (:p), (:e));" +
                    "insert into users_roles (user_id, role_id) " +
                            "values (select user_id from users where username = (:un), " +
                            " (select role_id from roles where role = 'USER'))")
    int signUpWithUserRole(@Param("fn") String firstName, @Param("ln") String lastName, @Param("un") String username,
                           @Param("p") String password, @Param("e") boolean enabled);

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE username = :username")
    User getUserByUsername(@Param("username") String username);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from users_roles where user_id = (:userId) and role_id = (:roleId);")
    int revokeRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "insert into users_roles (user_id, role_id) values (:userId, :roleId)")
    int addRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
    value = "delete from users_roles where user_id = (:userId);" +
            "delete from authors_articles aa where aa.user_id = (:userId);" +
            "delete from users where user_id = (:userId);")
    int deleteUserById(@Param("userId")Integer userId);

    @Query(nativeQuery = true, value =
            "select * from users where user_id in " +
            "(select user_id from users_roles ur inner join roles r on ur.role_id = r.role_id and r.role = 'AUTHOR');")
    Set<User> getAllAuthors();

    @Query(nativeQuery = true, value =
            "select * from users u inner join users_roles ur on u.user_id = ur.user_id" +
            " and u.user_id = (:userId) and ur.role_id = (select role_id from roles where role = 'AUTHOR')")
    User getAuthorByUserId(@Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "insert into authors_articles (user_id, article_id) values " +
            "((select user_id from users_roles where role_id = (select role_id from roles where role = 'AUTHOR') " +
            "and user_id = (:userId)), (:articleId))")
    void addArticleToAuthor(@Param("userId") Integer userId, @Param("articleId") Integer articleId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "delete from users_roles where user_id = (:userId) and role_id = (select role_id from roles where role ='AUTHOR');" +
            "delete from authors_articles where user_id = (:userId);")
    int removeAuthorRole(@Param("userId") Integer userId);
}
