package com.onebook.frontapi.service.coupon;

import com.onebook.frontapi.dto.coupon.request.coupon.IssueCouponToMemberRequest;
import com.onebook.frontapi.dto.coupon.response.coupon.CouponResponse;
import com.onebook.frontapi.dto.coupon.response.coupon.IssuedCouponFeignResponse;
import com.onebook.frontapi.dto.coupon.response.coupon.IssuedCouponResponse;
import com.onebook.frontapi.dto.coupon.response.couponPolicy.PricePolicyForBookResponse;
import com.onebook.frontapi.dto.coupon.response.couponPolicy.PricePolicyForCategoryResponse;
import com.onebook.frontapi.dto.coupon.response.couponPolicy.RatePolicyForBookResponse;
import com.onebook.frontapi.dto.coupon.response.couponPolicy.RatePolicyForCategoryResponse;
import com.onebook.frontapi.feign.coupon.CouponBoxClient;
import com.onebook.frontapi.feign.coupon.CouponClient;
import com.onebook.frontapi.feign.coupon.CouponPolicyClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CouponBoxService {

    private final CouponBoxClient couponBoxClient;
    private final CouponClient couponClient;
    private final CouponPolicyService couponPolicyService;
    private final CouponService couponService;

    private final Integer PAGE_SIZE = 10;

    public Page<IssuedCouponResponse> getIssuedCouponsByMemberId(int pageNo){

        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, Sort.by("issue_date_time").descending());

        Page<IssuedCouponFeignResponse> page = couponBoxClient.getIssuedCouponsByMemberId(pageable).getBody();

        List<IssuedCouponResponse> issuedCouponResponseList = new ArrayList<>();
//        Page<IssuedCouponResponse> response = new PageImpl(page.getContent());

        for(IssuedCouponFeignResponse issuedCouponFeignResponse :  page.getContent()){

            IssuedCouponResponse issuedCouponResponse = new IssuedCouponResponse();

            issuedCouponResponse.setCouponNumber(issuedCouponFeignResponse.getCouponNumber());
            issuedCouponResponse.setIssuedCouponId(issuedCouponFeignResponse.getIssuedCouponId());
            issuedCouponResponse.setMemberId(issuedCouponFeignResponse.getMemberId());
            issuedCouponResponse.setIssueDateTime(issuedCouponFeignResponse.getIssueDateTime());
            issuedCouponResponse.setUseDateTime(issuedCouponFeignResponse.getUseDateTime());

            // issued 쿠폰의 쿠폰번호로 - > 쿠폰 찾아옴
            CouponResponse couponResponse = couponClient.getCouponByCouponNumber(issuedCouponFeignResponse.getCouponNumber()).getBody();

            // 쿠폰의 정책 id로 -> 정책을 찾아옴
            if(Objects.nonNull(couponResponse.getPricePolicyForBookId())){

                PricePolicyForBookResponse pricePolicyForBookResponse =
                        couponPolicyService.getPricePolicyForBook(couponResponse.getPricePolicyForBookId());

                issuedCouponResponse.setMinimumOrderAmount(pricePolicyForBookResponse.getMinimumOrderAmount());
                issuedCouponResponse.setDiscountPrice(pricePolicyForBookResponse.getDiscountPrice());
                issuedCouponResponse.setExpirationPeriodStart(pricePolicyForBookResponse.getExpirationPeriodStart());
                issuedCouponResponse.setExpirationPeriodEnd(pricePolicyForBookResponse.getExpirationPeriodEnd());
                issuedCouponResponse.setName(pricePolicyForBookResponse.getName());
                issuedCouponResponse.setDescription(pricePolicyForBookResponse.getDescription());
                issuedCouponResponse.setBookName(pricePolicyForBookResponse.getBookName());
                issuedCouponResponse.setBookIsbn13(pricePolicyForBookResponse.getBookIsbn13());

                issuedCouponResponse.setCouponType("pricePolicyForBook");

            }

            if(Objects.nonNull(couponResponse.getPricePolicyForCategoryId())){

                PricePolicyForCategoryResponse pricePolicyForCategoryResponse =
                        couponPolicyService.getPricePolicyForCategory(couponResponse.getPricePolicyForCategoryId());

                issuedCouponResponse.setMinimumOrderAmount(pricePolicyForCategoryResponse.getMinimumOrderAmount());
                issuedCouponResponse.setDiscountPrice(pricePolicyForCategoryResponse.getDiscountPrice());
                issuedCouponResponse.setExpirationPeriodStart(pricePolicyForCategoryResponse.getExpirationPeriodStart());
                issuedCouponResponse.setExpirationPeriodEnd(pricePolicyForCategoryResponse.getExpirationPeriodEnd());
                issuedCouponResponse.setName(pricePolicyForCategoryResponse.getName());
                issuedCouponResponse.setDescription(pricePolicyForCategoryResponse.getDescription());
                issuedCouponResponse.setCategoryName(pricePolicyForCategoryResponse.getCategoryName());

                issuedCouponResponse.setCouponType("pricePolicyForCategory");

            }

            if(Objects.nonNull(couponResponse.getRatePolicyForBookId())){

                RatePolicyForBookResponse ratePolicyForBookResponse =
                        couponPolicyService.getRatePolicyForBook(couponResponse.getRatePolicyForBookId());

                issuedCouponResponse.setMinimumOrderAmount(ratePolicyForBookResponse.getMinimumOrderAmount());
                issuedCouponResponse.setDiscountRate(ratePolicyForBookResponse.getDiscountRate());
                issuedCouponResponse.setMaximumDiscountPrice(ratePolicyForBookResponse.getMaximumDiscountPrice());
                issuedCouponResponse.setExpirationPeriodStart(ratePolicyForBookResponse.getExpirationPeriodStart());
                issuedCouponResponse.setExpirationPeriodEnd(ratePolicyForBookResponse.getExpirationPeriodEnd());
                issuedCouponResponse.setName(ratePolicyForBookResponse.getName());
                issuedCouponResponse.setDescription(ratePolicyForBookResponse.getDescription());
                issuedCouponResponse.setBookName(ratePolicyForBookResponse.getBookName());
                issuedCouponResponse.setBookIsbn13(ratePolicyForBookResponse.getBookIsbn13());

                issuedCouponResponse.setCouponType("ratePolicyForBook");

            }

            if(Objects.nonNull(couponResponse.getRatePolicyForCategoryId())){

                RatePolicyForCategoryResponse ratePolicyForCategoryResponse =
                        couponPolicyService.getRatePolicyForCategory(couponResponse.getRatePolicyForCategoryId());

                issuedCouponResponse.setMinimumOrderAmount(ratePolicyForCategoryResponse.getMinimumOrderAmount());
                issuedCouponResponse.setDiscountRate(ratePolicyForCategoryResponse.getDiscountRate());
                issuedCouponResponse.setMaximumDiscountPrice(ratePolicyForCategoryResponse.getMaximumDiscountPrice());
                issuedCouponResponse.setExpirationPeriodStart(ratePolicyForCategoryResponse.getExpirationPeriodStart());
                issuedCouponResponse.setExpirationPeriodEnd(ratePolicyForCategoryResponse.getExpirationPeriodEnd());
                issuedCouponResponse.setName(ratePolicyForCategoryResponse.getName());
                issuedCouponResponse.setDescription(ratePolicyForCategoryResponse.getDescription());
                issuedCouponResponse.setCategoryName(ratePolicyForCategoryResponse.getCategoryName());

                issuedCouponResponse.setCouponType("ratePolicyForCategory");

            }

            issuedCouponResponseList.add(issuedCouponResponse);
        }


        Page<IssuedCouponResponse> issuedCouponResponsePage =
                new PageImpl<>(issuedCouponResponseList,pageable,page.getTotalPages());

            return issuedCouponResponsePage;

    }

    public IssuedCouponResponse issueCouponToMember(){

        // 쿠폰 번호가 필요함 (정책id로 해당하는 쿠폰들 쫙 받아온다음에, 그중에 하나 랜덤으로 사용자에게 발급)

        // 정률정책이면, 정액정책이면...

        String couponNumber;
        IssueCouponToMemberRequest issueCouponToMemberRequest = new IssueCouponToMemberRequest("{couponNumber}");

        return couponBoxClient.issueCouponToMember(issueCouponToMemberRequest).getBody();
    }

}
