package com.axio.airlinereservation.service;

import com.axio.airlinereservation.exception.CustomException;
import com.axio.airlinereservation.entity.Coupon;
import com.axio.airlinereservation.repository.CouponRepository;
import com.axio.airlinereservation.request.coupon.CouponRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class CouponService {
    @Autowired
    private CouponRepository couponRepository;

    public String addCoupon(@Valid @NotNull CouponRequest couponRequest) {
        String code = couponRequest.getCode();
        if (couponRepository.existsById(code)) {
            throw new CustomException("Coupon already exists for code :: " + code);
        }
        if (couponRequest.getDiscountPercentage() < 0 || couponRequest.getDiscountPercentage() > 100) {
            throw new CustomException("Invalid discount percentage in coupon: " + code);
        }
        Coupon newCoupon=new Coupon();
        newCoupon.setCode(couponRequest.getCode());
        newCoupon.setDiscountAmount(couponRequest.getDiscountAmount());
        newCoupon.setDiscountPercentage(couponRequest.getDiscountPercentage());
        couponRepository.save(newCoupon);
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
