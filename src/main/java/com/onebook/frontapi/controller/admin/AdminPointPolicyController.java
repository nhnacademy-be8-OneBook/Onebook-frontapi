package com.onebook.frontapi.controller.admin;

import com.onebook.frontapi.dto.point.request.PointPolicyRequest;
import com.onebook.frontapi.dto.point.response.PointPolicyResponse;
import com.onebook.frontapi.service.point.AdminPointPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/point-policies")
public class AdminPointPolicyController {

    final AdminPointPolicyService adminPointPolicyService;

    // 조회하기
    @GetMapping
    public String getPointPolicies(@PageableDefault(size = 5) Pageable pageable, Model model) {
        Page<PointPolicyResponse> pointPolicies = adminPointPolicyService.getPointPolicies(pageable);
        model.addAttribute("pointPolicies", pointPolicies);
        return "admin/point/admin-point";
    }

    // 정책 추가
    @PostMapping
    public String createPointPolicy(@ModelAttribute PointPolicyRequest pointPolicyRequest) {
        adminPointPolicyService.createPointPolicy(pointPolicyRequest);
        return "redirect:/admin/point-policies";
    }

    // 정책 수정
    @PutMapping("/{pointPolicyId}")
    public ResponseEntity<Void> updatePointPolicy(@PathVariable Long pointPolicyId,
                                                  @RequestBody PointPolicyRequest pointPolicyRequest) {
        adminPointPolicyService.updatePointPolicy(pointPolicyId, pointPolicyRequest);
        return ResponseEntity.ok().build();
    }

    // 정책 삭제
    @DeleteMapping("/{pointPolicyId}")
    public ResponseEntity<Void> deletePointPolicy(@PathVariable Long pointPolicyId) {
        adminPointPolicyService.deletePointPolicy(pointPolicyId);
        return ResponseEntity.ok().build();
    }

    // 정책 상세 조회
    @GetMapping("/{pointPolicyId}")
    public ResponseEntity<PointPolicyResponse> getPointPolicy(@PathVariable Long pointPolicyId) {
        PointPolicyResponse pointPolicy = adminPointPolicyService.getPointPolicyById(pointPolicyId);
        return ResponseEntity.ok(pointPolicy);
    }
}
