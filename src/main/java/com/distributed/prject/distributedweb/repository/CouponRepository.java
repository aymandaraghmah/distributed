package com.distributed.prject.distributedweb.repository;

import com.distributed.prject.distributedweb.model.Coupon;
import com.distributed.prject.distributedweb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Integer> {
    @Query("select c from Coupon c where c.id = :id")
    Coupon findById(@Param("id") int id);

    @Query("select new com.distributed.prject.distributedweb.model.Coupon(c.id,c.name,c.description) from Coupon c join User a where a.id = :id")
    List<Coupon> findUserCoupons(@Param("id") int id);

}
