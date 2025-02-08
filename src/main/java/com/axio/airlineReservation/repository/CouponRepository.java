package com.axio.airlineReservation.repository;

import com.axio.airlineReservation.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,String> {
}
