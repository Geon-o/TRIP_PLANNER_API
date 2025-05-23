package com.example.trip_planner.auth.controller;

import com.example.trip_planner.auth.service.JwtAuthServiceImpl;
import com.example.trip_planner.member.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class JwtAuthController {

    private final JwtAuthServiceImpl jwtService;

    @PostMapping("/reIssuanceToken")
    public ResponseEntity<?> reIssuanceToken(HttpServletRequest request) {
        return jwtService.reIssuanceToken(request);
    }
}
