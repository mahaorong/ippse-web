package com.ippse.web.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.ippse.web.domain.App;

import org.springframework.data.domain.Pageable;

public interface AppRepository extends PagingAndSortingRepository<App, String> {

    @Query(value = "SELECT * FROM app WHERE userid = ?1", countQuery = "SELECT count(*) FROM app WHERE userid = ?1", nativeQuery = true)
    Page<App> findByUserid(@Param("userid") String id, Pageable var1);

    @Override
    void delete(App app);
}
