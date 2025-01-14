package com.onebook.frontapi.feign.point;

import com.onebook.frontapi.dto.point.response.PointLogResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "pointFeignClient", url = "${onebook.gatewayUrl}")
public interface PointFeignClient {

    /**
     * 회원의 포인트 내역을 가져오는 API 호출.
     *
     * @return ResponseEntity로 감싼 Page<PointLogResponse> 타입의 포인트 내역 목록
     */
    @GetMapping("/task/member/point-logs")
    ResponseEntity<Page<PointLogResponse>> getUserPointLogs();
}
