package com.onebook.frontapi.controller.coupon;

import com.onebook.frontapi.dto.coupon.request.coupon.IssueCouponToMemberRequest;
import com.onebook.frontapi.dto.coupon.response.coupon.IssuedCouponResponse;
import com.onebook.frontapi.dto.coupon.response.couponPolicy.UsingPolicyResponse;
import com.onebook.frontapi.feign.coupon.CouponPolicyClient;
import com.onebook.frontapi.service.coupon.CouponBoxService;
import com.onebook.frontapi.service.coupon.CouponPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberCouponController {

    private final CouponBoxService couponBoxService;
    private final CouponPolicyService couponPolicyService;

    @GetMapping("/coupon/coupon-box")
    public String getMyCouponBox(Model model,
                                 @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo){

        Page<IssuedCouponResponse> issuedCouponResponses = couponBoxService.getIssuedCouponsByMemberId(pageNo);
        model.addAttribute("issuedCouponResponses",issuedCouponResponses);

        return "coupon/coupon/my-coupon-list";
    }

    @GetMapping("/coupon/coupon-holder")
    public String getCouponHolder(Model model){

        List<UsingPolicyResponse> usingPolicies = couponPolicyService.getUsingPolicies();

        model.addAttribute("usingPolicies", usingPolicies);

        return "coupon/coupon/coupon-holder";
    }

    @PostMapping("/coupon/coupon-holder/issue")
    public String getCoupon(){

        couponBoxService.issueCouponToMember();

        return "redirect:/coupon/coupon-holder";
    }

}
