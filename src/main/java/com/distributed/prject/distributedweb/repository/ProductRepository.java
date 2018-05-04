package com.distributed.prject.distributedweb.repository;

import com.distributed.prject.distributedweb.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository  extends JpaRepository<Product,Integer> {
    @Query("select u from Product u where u.id = :id")
    Product findById(@Param("id") int id);
}
