package com.example.trip_planner.jwt.auth.controller;

import com.example.trip_planner.jwt.auth.dto.JwtTokenDto;
import com.example.trip_planner.jwt.auth.service.JwtAuthServiceImpl;
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
    public ResponseEntity<JwtTokenDto> reIssuanceToken(HttpServletRequest request) {
        return jwtService.reIssuanceToken(request);
    }
}
