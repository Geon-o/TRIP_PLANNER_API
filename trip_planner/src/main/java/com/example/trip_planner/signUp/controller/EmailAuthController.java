package com.example.trip_planner.signUp.controller;

import com.example.trip_planner.user.signUp.request.EmailAuthRequest;
import com.example.trip_planner.user.signUp.service.EmailAuthServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/emailAuth")
@RequiredArgsConstructor
public class EmailAuthController {

    private final EmailAuthServiceImpl service;

    @PostMapping("/sendVerificationNo")
    public void sendVerificationNo(@RequestBody EmailAuthRequest request) {

        service.registerVerificationNo(request);
        log.info("email : {}", request.getEmail());

    }
}

