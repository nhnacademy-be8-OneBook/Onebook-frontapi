package com.onebook.frontapi.service.point.impl;

import com.onebook.frontapi.dto.point.response.PointLogResponse;
import com.onebook.frontapi.feign.point.PointFeignClient;
import com.onebook.frontapi.service.point.PointService;
import feign.FeignException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
public class PointServiceImpl implements PointService {

    private final PointFeignClient pointFeign;

    public PointServiceImpl(PointFeignClient pointFeign) {
        this.pointFeign = pointFeign;
    }

    @Override
    public Page<PointLogResponse> getMemberPointHistories() {
        try {
            // Feign 클라이언트로부터 포인트 내역 가져오기
            Page<PointLogResponse> memberPointLogs = pointFeign.getUserPointLogs().getBody();
            return memberPointLogs;
        } catch (FeignException.Unauthorized ex) {
            // 인증 실패 시 401 에러
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인되지 않은 상태에서 포인트 내역을 조회할 수 없습니다.", ex);
        } catch (FeignException.BadRequest ex) {
            // 잘못된 요청 시 400 에러
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", ex);
        } catch (FeignException.NotFound ex) {
            // 포인트 내역이 없을 때 404 에러
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "포인트 내역을 찾을 수 없습니다.", ex);
        } catch (FeignException.InternalServerError ex) {
            // 서버 오류 시 500 에러
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류", ex);
        } catch (Exception ex) {
            // 그 외 모든 예외에 대해 500 에러 처리
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 오류가 발생했습니다.", ex);
        }
    }
}
