package com.ippse.web.dao;

import java.math.BigInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.ippse.web.domain.Comment;

public interface CommentRepository extends PagingAndSortingRepository<Comment, String> {

    @Query(value = "SELECT * FROM comment WHERE appid = ?1", countQuery = "SELECT count(*) FROM comment WHERE appid = ?1", nativeQuery = true)
    Page<Comment> findByAppId(@Param("appid") String id, Pageable var1);

    @Override
    void delete(Comment comment);

    @Override
    void deleteById(String s);

    @Query(value = "select count(*) from ipsite.Comment where appid= ?1", nativeQuery = true)
    BigInteger findCount(@Param("appid")String aid);
}
