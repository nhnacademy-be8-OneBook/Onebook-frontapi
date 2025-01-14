package com.onebook.frontapi.dto.point.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PointPolicyRequest {
    private String pointPolicyName;
    private Integer pointPolicyRate;
    private Integer pointPolicyConditionAmount;
    private Integer pointPolicyApplyAmount;
    private String pointPolicyCondition;
    private boolean pointPolicyApplyType;
}