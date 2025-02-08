package com.axio.airlineReservation.service;

import com.axio.airlineReservation.Exception.CustomException;
import com.axio.airlineReservation.entity.Coupon;
import com.axio.airlineReservation.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
public class CouponService {
    @Autowired
    private CouponRepository couponRepository;

    public String addCoupon(@Valid @NotNull Coupon coupon) {
        String code = coupon.getCode();
        if (couponRepository.existsById(code)) {
            throw new CustomException("Coupon already exists for code :: " + code);
        }
        if (coupon.getDiscountPercentage() < 0 || coupon.getDiscountPercentage() > 100) {
            throw new CustomException("Invalid discount percentage in coupon: " + code);
        }
        couponRepository.save(coupon);
        return "Coupon added successfully";
    }

    public List<Coupon> getAllCoupon() {
        return couponRepository.findAll();
    }

    public Double applyCoupon(String code, double fare){
        Optional<Coupon> couponOptional = couponRepository.findById(code);

        if (couponOptional.isEmpty()) {
            throw new CustomException("Invalid coupon code: " + code);
        }

        Coupon coupon = couponOptional.get();
        double discountPercentage = coupon.getDiscountPercentage();
        double maxDiscountAmount=coupon.getDiscountAmount();

        double discountAmount = (fare * discountPercentage) / 100;
        double finalDiscountAmount= Math.min(maxDiscountAmount,discountAmount);
        double discountedFare = fare - finalDiscountAmount;

        if (discountedFare < 0) {
            discountedFare = 0;
        }

        return discountedFare;

    }
}
