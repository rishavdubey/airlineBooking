package com.axio.airlinereservation.controller;

import com.axio.airlinereservation.common.CommonValidation;
import com.axio.airlinereservation.entity.Coupon;
import com.axio.airlinereservation.request.coupon.CouponRequest;
import com.axio.airlinereservation.response.CustomMessage;
import com.axio.airlinereservation.service.CouponService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/coupons")
class CouponController {

    @Autowired
    private CouponService couponService;
    @Autowired
    private CommonValidation commonValidation;

    @PostMapping("/add")
    public ResponseEntity<CustomMessage> addCoupon(@Valid @RequestBody CouponRequest couponRequest, @CookieValue(name = "JWT_TOKEN", required = false) String token){
        commonValidation.validateToken(token);
        String message=couponService.addCoupon(couponRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomMessage(message,"200"));
    }

    @GetMapping("/apply")
    public ResponseEntity<Map<String,Double>> applyCoupon(@RequestParam("code") String code, @RequestParam("fare") double fare) {
        double discountFare = couponService.applyCoupon(code,fare);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("discountFare",discountFare));
    }

    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupon(){
        return ResponseEntity.status(HttpStatus.OK).body(couponService.getAllCoupon());
    }
}