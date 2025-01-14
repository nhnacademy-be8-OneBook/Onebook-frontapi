package com.onebook.frontapi.controller.my.point;

import com.onebook.frontapi.dto.point.response.PointLogResponse;
import com.onebook.frontapi.service.point.PointService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/my/point")
public class PointController {

    private final PointService pointService;

    @Autowired
    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping("/point")
    public ModelAndView getUserPointHistories(HttpSession session) {
        Page<PointLogResponse> memberPointHistories = pointService.getMemberPointHistories();

        ModelAndView modelAndView = new ModelAndView("my/point/point");
        modelAndView.addObject("memberPointHistories", memberPointHistories);

        return modelAndView;
    }
}
