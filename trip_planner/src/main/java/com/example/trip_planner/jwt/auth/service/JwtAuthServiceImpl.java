package com.example.trip_planner.jwt.auth.service;

import com.example.trip_planner.config.redis.service.RedisService;
import com.example.trip_planner.jwt.JwtUtil;
import com.example.trip_planner.jwt.auth.dto.JwtTokenDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtAuthServiceImpl {

    private final RedisService redisService;
    private final JwtUtil jwtUtil;

    public ResponseEntity<JwtTokenDto> reIssuanceToken(HttpServletRequest request) {
        String refreshToken = extractRefreshTokenFromCookie(request);

        Long memNo = redisService.getValueByKey(refreshToken);
        if (memNo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String newAccessToken = jwtUtil.createAccessToken(String.valueOf(memNo));
        JwtTokenDto jwtTokenDto = JwtTokenDto.builder()
                .newAccessToken(newAccessToken)
                .userId(memNo)
                .build();

        return ResponseEntity.ok().body(jwtTokenDto);
    }

    private String extractRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {

            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
