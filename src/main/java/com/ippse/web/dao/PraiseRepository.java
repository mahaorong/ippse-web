package com.ippse.web.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.ippse.web.domain.Praise;


public interface PraiseRepository extends PagingAndSortingRepository<Praise, String> {

    @Override
    void delete(Praise praise);

    @Query("from Praise where commentid=:commentid and userid=:userid")
    Optional<Praise> findByCommentidAndUserid(@Param("commentid")String commentid, @Param("userid")String userid);

}
