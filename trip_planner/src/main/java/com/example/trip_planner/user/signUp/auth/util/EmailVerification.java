package com.example.trip_planner.user.signUp.auth.util;

import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailVerification {

    /**
     * 전달받은 이메일이 정상적인 이메일인지 확인하는 로직
     * @param email
     */
    public void emailVerification(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:naver|gmail)\\.com$";

        if (!email.matches(emailRegex)) {
            throw new IllegalArgumentException("email is not valid");
        }
    }
}
