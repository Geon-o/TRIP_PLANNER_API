package com.example.trip_planner.user.signUp.service;

import com.example.trip_planner.config.mail.service.MailService;
import com.example.trip_planner.user.signUp.request.CheckAuthTokenRequest;
import com.example.trip_planner.user.signUp.request.EmailAuthRequest;
import com.example.trip_planner.user.signUp.util.AuthTokenIssuance;
import com.example.trip_planner.user.signUp.util.EmailVerification;
import com.example.trip_planner.config.redis.service.RedisService;
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

    /**
     * 이메일로 발송된 인증번호 확인하는 로직
     * @param request
     * @return
     */
    public boolean checkVerificationNo(CheckAuthTokenRequest request) {
        String token = String.valueOf(redisService.getValueByKey(request.getEmail()));

        if (token.equals(request.getAuthToken())) {
            redisService.deleteByKey(request.getEmail());
            return true;
        }

        return false;
    }
}
