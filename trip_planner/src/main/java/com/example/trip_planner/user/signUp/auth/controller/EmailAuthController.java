package com.example.trip_planner.user.signUp.auth.controller;

import com.example.trip_planner.user.signUp.auth.request.EmailAuthRequest;
import com.example.trip_planner.user.signUp.auth.service.EmailAuthServiceImpl;
import com.example.trip_planner.user.signUp.auth.request.CheckAuthTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emailAuth")
@RequiredArgsConstructor
public class EmailAuthController {

    private final EmailAuthServiceImpl service;

    @PostMapping("/sendVerificationNo")
    public void sendVerificationNo(@RequestBody EmailAuthRequest request) {
        service.registerVerificationNo(request);
    }

    @PostMapping("/checkVerificationNo")
    public boolean checkVerificationNo(@RequestBody CheckAuthTokenRequest request) {
        return service.checkVerificationNo(request);
    }
}

