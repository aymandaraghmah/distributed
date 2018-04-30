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

    @Query("select distinct c from Coupon c where c.id in (select e.coupon_id from user_coupons e where e.user_id = :id)")
    List<Coupon> findUserCoupons(@Param("id") int id);

}
