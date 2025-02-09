package com.axio.airlinereservation.repository;

import com.axio.airlinereservation.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,String> {
}
