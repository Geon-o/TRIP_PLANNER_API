package com.example.trip_planner.signUp.service;

import com.example.trip_planner.config.mail.service.MailService;
import com.example.trip_planner.user.signUp.request.EmailAuthRequest;
import com.example.trip_planner.user.signUp.util.AuthTokenIssuance;
import com.example.trip_planner.config.redis.service.RedisService;
import com.example.trip_planner.user.signUp.util.EmailVerification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailAuthServiceImpl {

    private final RedisService redisService;
    private final AuthTokenIssuance tokenIssuance;
    private final MailService mailService;
    private final EmailVerification emailVerification;

    /**
     * 추가적으로 유효한 이메일인지 확인하는 로직 구성
     * @param request
     */

    /**
     * 토큰 발급 후 레디스 등록 및 이메일 발송
     * @param request
     */
    public void registerVerificationNo(EmailAuthRequest request) {

        try {
            emailVerification.emailVerification(request.getEmail());

            String token = tokenIssuance.issueToken();
            redisService.setKeyAndValue(request.getEmail(), token, request.getDeadlineTime());
            sendTokenToEmail(request.getEmail(), token);

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("email is not valid");
        }
    }

    public void sendTokenToEmail(String email, String token) {
        try {
            mailService.sendMail(email, token);

        } catch (Exception e) {
            log.info("메일 전송 실패!");
        }

    }
}
