package com.onebook.frontapi.feign.coupon;

import com.onebook.frontapi.dto.coupon.request.coupon.IssueCouponToMemberRequest;
import com.onebook.frontapi.dto.coupon.response.coupon.IssuedCouponFeignResponse;
import com.onebook.frontapi.dto.coupon.response.coupon.IssuedCouponResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "CouponBoxClient",  url = "${onebook.gatewayUrl}")
public interface CouponBoxClient {

    @PostMapping("/task/coupon/issue/welcome/{login-id}")
    ResponseEntity<IssuedCouponFeignResponse> issueWelcomeCouponToMember(@PathVariable(name = "login-id") String loginId);

    @GetMapping("/task/coupon/coupon-box")
    ResponseEntity<Page<IssuedCouponFeignResponse>> getIssuedCouponsByMemberId(Pageable pageable);

    @PostMapping("/task/coupon/issue")
    ResponseEntity<IssuedCouponResponse> issueCouponToMember(@RequestBody IssueCouponToMemberRequest issueCouponToMemberRequest);
}
