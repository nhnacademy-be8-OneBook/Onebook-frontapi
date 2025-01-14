package com.onebook.frontapi.service.point.impl;

import com.onebook.frontapi.adaptor.point.PointPolicyAdaptor;
import com.onebook.frontapi.dto.point.request.PointPolicyRequest;
import com.onebook.frontapi.dto.point.response.PointPolicyResponse;
import com.onebook.frontapi.service.point.AdminPointPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminPointPolicyServiceImpl implements AdminPointPolicyService {

    private final PointPolicyAdaptor pointPolicyAdaptor;

    @Override
    public Page<PointPolicyResponse> getPointPolicies(Pageable pageable) {
        return pointPolicyAdaptor.getPointPolicies(pageable).getBody();
    }

    @Override
    public PointPolicyResponse createPointPolicy(PointPolicyRequest pointPolicyRequest) {
        return pointPolicyAdaptor.createPointPolicy(pointPolicyRequest).getBody();
    }

    @Override
    public PointPolicyResponse updatePointPolicy(Long pointPolicyId, PointPolicyRequest pointPolicyRequest) {
        return pointPolicyAdaptor.updatePointPolicy(pointPolicyId, pointPolicyRequest).getBody();
    }

    @Override
    public void deletePointPolicy(Long pointPolicyId) {
        pointPolicyAdaptor.deletePointPolicy(pointPolicyId);
    }

    @Override
    public PointPolicyResponse getPointPolicyById(Long pointPolicyId) {
        return pointPolicyAdaptor.getPointPolicy(pointPolicyId).getBody();
    }
}