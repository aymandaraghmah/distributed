package com.distributed.prject.distributedweb.repository;

import com.distributed.prject.distributedweb.model.FoodCategory;
import com.distributed.prject.distributedweb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<FoodCategory, Integer> {

    @Query("select u from FoodCategory u where u.id = :id")
    FoodCategory findById(@Param("id") int id);

}

