package com.example.trip_planner.user.signUp.controller;

import com.example.trip_planner.user.signUp.request.CheckAuthTokenRequest;
import com.example.trip_planner.user.signUp.request.EmailAuthRequest;
import com.example.trip_planner.user.signUp.service.EmailAuthServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

