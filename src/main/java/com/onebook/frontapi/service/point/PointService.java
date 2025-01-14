package com.onebook.frontapi.service.point;

import com.onebook.frontapi.dto.point.response.PointLogResponse;
import org.springframework.data.domain.Page;

public interface PointService {
    // 포인트 내역 가져오기
    Page<PointLogResponse> getMemberPointHistories();
}