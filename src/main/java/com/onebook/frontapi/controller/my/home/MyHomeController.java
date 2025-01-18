package com.onebook.frontapi.controller.my.home;

import com.onebook.frontapi.dto.member.MemberResponse;
import com.onebook.frontapi.dto.myhome.MyOrderStatusResponse;
import com.onebook.frontapi.dto.order.OrderResponse;
import com.onebook.frontapi.service.coupon.CouponBoxService;
import com.onebook.frontapi.service.coupon.CouponService;
import com.onebook.frontapi.service.member.MemberService;
import com.onebook.frontapi.service.myhome.MyHomeService;
import com.onebook.frontapi.service.order.OrderService;
import com.onebook.frontapi.service.point.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/my")
public class MyHomeController {

    private final MemberService memberService;
    private final OrderService orderService;
    private final MyHomeService myHomeService;
    private final CouponBoxService couponBoxService;

    @GetMapping("/home")
    public String myHome(Pageable pageable, Model model) {
        MemberResponse memberResponse = memberService.getMember();
        Page<OrderResponse> orderResponses = orderService.getAllOrders(pageable);
        MyOrderStatusResponse myOrderStatusResponse = myHomeService.getMyOrderStatus(orderResponses);
        Integer memberNetPaymentAmount = memberService.getMemberNetPaymentAmount();
        Long totalCoupons = couponBoxService.getIssuedCouponsByMemberId(0).getTotalElements();

        model.addAttribute("member", memberResponse);
        model.addAttribute("myOrderStatus", myOrderStatusResponse);
        model.addAttribute("memberNetPaymentAmount", memberNetPaymentAmount);
        model.addAttribute("totalCoupons", totalCoupons);
        return "my/home/home";
    }

}
