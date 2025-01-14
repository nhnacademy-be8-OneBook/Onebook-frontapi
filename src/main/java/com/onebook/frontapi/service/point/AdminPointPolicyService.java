package com.onebook.frontapi.service.point;

import com.onebook.frontapi.dto.point.request.PointPolicyRequest;
import com.onebook.frontapi.dto.point.response.PointPolicyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminPointPolicyService {

    Page<PointPolicyResponse> getPointPolicies(Pageable pageable);

    PointPolicyResponse createPointPolicy(PointPolicyRequest pointPolicyRequest);

    PointPolicyResponse updatePointPolicy(Long pointPolicyId, PointPolicyRequest pointPolicyRequest);

    void deletePointPolicy(Long pointPolicyId);

    PointPolicyResponse getPointPolicyById(Long pointPolicyId);
}
