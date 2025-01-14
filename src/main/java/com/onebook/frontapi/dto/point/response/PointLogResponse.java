package com.onebook.frontapi.dto.point.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;
import com.onebook.frontapi.enums.PointHistoryType;  // enum 패키지 임포트

@Getter
@AllArgsConstructor
public class PointLogResponse {
    private int pointCurrent;                // 현재 포인트
    private PointHistoryType pointHistoryType; // 포인트 업데이트 타입 (ex. 증가, 감소 등) - enum 타입
    private int pointLogAmount;              // 포인트 변화량
    private LocalDateTime pointLogUpdatedAt;  // 포인트 업데이트 일시
}
