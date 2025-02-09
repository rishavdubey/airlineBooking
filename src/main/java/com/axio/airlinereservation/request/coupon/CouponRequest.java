package com.axio.airlinereservation.request.coupon;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CouponRequest {

    @NotBlank(message = "Code cannot be blank")
    private String code;

    @NotNull(message = "Discount Percentage is mandatory")
    private Double discountPercentage;

    @NotNull(message = "Discount Amount is mandatory")
    private Double discountAmount;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }
}
