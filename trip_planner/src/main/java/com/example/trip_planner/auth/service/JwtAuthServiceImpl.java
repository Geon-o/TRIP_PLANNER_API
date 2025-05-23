package com.example.trip_planner.auth.service;

import com.example.trip_planner.config.redis.service.RedisService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtAuthServiceImpl {

    private final RedisService redisService;

    public ResponseEntity<?> reIssuanceToken(HttpServletRequest request) {
        String refreshToken = extractRefreshTokenFromCookie(request);


        Long memNo = redisService.getValueByKey(refreshToken);
        if (memNo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "토큰이 유효하지 않습니다."));
        }

        UUID newRefreshToken = UUID.randomUUID();

        redisService.deleteByKey(refreshToken);
        redisService.setKeyAndValue(newRefreshToken.toString(), String.valueOf(memNo), 604800);

        return null;
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
