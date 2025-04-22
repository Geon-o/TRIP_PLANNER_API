package com.example.trip_planner.member.controller;

import com.example.trip_planner.member.request.signUp.EmailAuthRequest;
import com.example.trip_planner.member.request.signUp.SignUpRequest;
import com.example.trip_planner.member.service.MemberServiceImpl;
import com.example.trip_planner.member.request.signUp.CheckAuthTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberServiceImpl service;

    @PostMapping("/emailAuth/sendVerificationNo")
    public void sendVerificationNo(@RequestBody EmailAuthRequest request) {
        service.registerVerificationNo(request);
    }

    @PostMapping("/emailAuth/checkVerificationNo")
    public boolean checkVerificationNo(@RequestBody CheckAuthTokenRequest request) {
        return service.checkVerificationNo(request);
    }

    @GetMapping("/checkDuplicateUserId/{userId}")
    public boolean checkDuplicateUserId(@PathVariable("userId") String userId) {
        return service.checkDuplicateUserId(userId);
    }

    @PostMapping("/signUp")
    public void signUp(@RequestBody SignUpRequest request) {
        service.signUp(request);
    }
}

