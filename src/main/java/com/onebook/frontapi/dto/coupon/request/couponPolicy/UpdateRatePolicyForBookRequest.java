package com.onebook.frontapi.dto.coupon.request.couponPolicy;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class UpdateRatePolicyForBookRequest {

    private Long id;
    @NotNull
    private Integer discountRate;
    @NotNull
    private Integer minimumOrderAmount;
    @NotNull
    private Integer maximumDiscountPrice;
    @NotNull
    private LocalDateTime expirationPeriodStart;
    @NotNull
    private LocalDateTime expirationPeriodEnd;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private String bookIsbn13;

}
