//package com.onebook.frontapi.point;
//
//import com.onebook.frontapi.dto.point.response.PointLogResponse;
//import com.onebook.frontapi.feign.point.PointFeignClient;
//import com.onebook.frontapi.service.point.impl.PointServiceImpl;
//import com.onebook.frontapi.enums.PointHistoryType;
//import feign.FeignException;
//import feign.Request;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.nio.charset.StandardCharsets;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
//import static org.mockito.BDDMockito.given;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.springframework.http.HttpStatus.*;
//
//@SpringBootTest
//public class FeignAndFrontConnectionTest {
//
//    @InjectMocks
//    private PointServiceImpl pointService;  // PointServiceImpl로 수정
//
//    @Mock
//    private PointFeignClient pointFeignClient;
//
//    private static final String TEST_URL = "http://localhost:8210"; // FeignClient의 실제 URL(gatewayUrl)
//
//    @BeforeEach
//    public void setUp() {
//        // 각 테스트가 실행되기 전에 호출되는 초기화 메서드
//    }
//
//    @Test
//    public void testGetMemberPointHistories_Success() {
//        // 정상적인 FeignClient 응답을 mock
//        PointLogResponse mockResponse1 = new PointLogResponse(100, PointHistoryType.PURCHASE, 100, LocalDateTime.now());
//        PointLogResponse mockResponse2 = new PointLogResponse(200, PointHistoryType.CASHBACK, 200, LocalDateTime.now());
//
//        List<PointLogResponse> mockPointLogs = Arrays.asList(mockResponse1, mockResponse2);
//        Page<PointLogResponse> mockPage = new PageImpl<>(mockPointLogs);
//
//        // FeignClient가 정상적으로 반환하는 값을 mock
//        given(pointFeignClient.getUserPointLogs())
//                .willReturn(ResponseEntity.ok(mockPage));
//
//        // PointService 메서드 호출
//        Page<PointLogResponse> result = pointService.getMemberPointHistories();
//
//        // 결과 검증
//        assertThat(result).isNotEmpty();
//        assertThat(result.getContent()).hasSize(2);
//        assertThat(result.getContent().get(0).getPointLogAmount()).isEqualTo(100);
//
//        // Enum 타입을 문자열로 비교하기 위해 .toString() 사용
//        assertThat(result.getContent().get(1).getPointHistoryType().toString()).isEqualTo(PointHistoryType.CASHBACK.toString());
//    }
//
//    @Test
//    public void testGetMemberPointHistories_Unauthorized() {
//        // Unauthorized 예외 발생
//        byte[] responseBody = "Unauthorized".getBytes(StandardCharsets.UTF_8);
//
//        // Request 객체를 mock하여 전달
//        Request mockRequest = Request.create(Request.HttpMethod.GET, TEST_URL, Map.of(), null, StandardCharsets.UTF_8);
//
//        FeignException.Unauthorized unauthorizedException = new FeignException.Unauthorized(
//                "Unauthorized",
//                mockRequest,
//                responseBody,
//                Map.of()
//        );
//
//        // Unauthorized 예외를 mock
//        given(pointFeignClient.getUserPointLogs())
//                .willThrow(unauthorizedException);
//
//        // 예외가 발생해야 하므로 해당 예외가 발생하는지 검증
//        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
//            pointService.getMemberPointHistories();
//        });
//
//        assertThat(exception.getStatusCode()).isEqualTo(UNAUTHORIZED);
//        assertThat(exception.getReason()).isEqualTo("로그인되지 않은 상태에서 포인트 내역을 조회할 수 없습니다.");
//    }
//
//    @Test
//    public void testGetMemberPointHistories_BadRequest() {
//        // Bad Request 예외 발생
//        byte[] responseBody = "Bad Request".getBytes(StandardCharsets.UTF_8);
//
//        // Request 객체를 mock하여 전달
//        Request mockRequest = Request.create(Request.HttpMethod.GET, TEST_URL, Map.of(), null, StandardCharsets.UTF_8);
//
//        FeignException.BadRequest badRequestException = new FeignException.BadRequest(
//                "Bad Request",
//                mockRequest,
//                responseBody,
//                Map.of()
//        );
//
//        // BadRequest 예외를 mock
//        given(pointFeignClient.getUserPointLogs())
//                .willThrow(badRequestException);
//
//        // 예외가 발생해야 하므로 해당 예외가 발생하는지 검증
//        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
//            pointService.getMemberPointHistories();
//        });
//
//        assertThat(exception.getStatusCode()).isEqualTo(BAD_REQUEST);
//        assertThat(exception.getReason()).isEqualTo("잘못된 요청입니다.");
//    }
//
//    @Test
//    public void testGetMemberPointHistories_InternalServerError() {
//        // Internal Server Error 예외 발생
//        byte[] responseBody = "Internal Server Error".getBytes(StandardCharsets.UTF_8);
//
//        // Request 객체를 mock하여 전달
//        Request mockRequest = Request.create(Request.HttpMethod.GET, TEST_URL, Map.of(), null, StandardCharsets.UTF_8);
//
//        FeignException.InternalServerError internalServerError = new FeignException.InternalServerError(
//                "Internal Server Error",
//                mockRequest,
//                responseBody,
//                Map.of()
//        );
//
//        // Internal Server Error 예외를 mock
//        given(pointFeignClient.getUserPointLogs())
//                .willThrow(internalServerError);
//
//        // 예외가 발생해야 하므로 해당 예외가 발생하는지 검증
//        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
//            pointService.getMemberPointHistories();
//        });
//
//        assertThat(exception.getStatusCode()).isEqualTo(INTERNAL_SERVER_ERROR);
//        assertThat(exception.getReason()).isEqualTo("서버 오류");
//    }
//
//    @Test
//    public void testGetMemberPointHistories_IllegalStateException() {
//        // IllegalStateException을 강제로 발생시키는 경우
//        given(pointFeignClient.getUserPointLogs())
//                .willThrow(new IllegalStateException("잘못된 상태"));
//
//        // 예외가 발생해야 하므로 해당 예외가 발생하는지 검증
//        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
//            pointService.getMemberPointHistories();
//        });
//
//        assertThat(exception.getStatusCode()).isEqualTo(INTERNAL_SERVER_ERROR);
//        assertThat(exception.getReason()).isEqualTo("서버에서 오류가 발생했습니다.");
//    }
//}
