package com.hamidur.ss.dao.repos;

import com.hamidur.ss.dao.models.Comment;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer>
{
    // returns All Comments
    List<Comment> findAll();

    // returns a Comment associated with the id
    Comment findByCommentId(Integer commentId);

    // returns all Comments on an Article associated with this id
    @Query(nativeQuery = true,
            value = "select * from comments where article_id = (:article_id)")
    List<Comment> getCommentsByArticleId(@Param("article_id") Integer articleId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "update comments set comment = (:comment) where comment_id = (:commentId) and article_id = (:articleId)")
    void updateCommentByCommentIdAndArticleId(@Param("comment") String comment,
                                              @Param("commentId") Integer commentId,
                                              @Param("articleId") Integer articleId);
}
