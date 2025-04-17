package com.example.trip_planner.member.util;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthTokenIssuance {

    /**
     * 이메일 인증번호 발급 로직
     * @return
     */
    public String issueToken() {
        String token = "";

        StringBuilder sb = new StringBuilder();
        sb.append(java.util.stream.IntStream.range(0, 6)
                 .mapToObj(i -> String.valueOf((int)(Math.random() * 10)))
                 .collect(java.util.stream.Collectors.joining()));

        token = sb.toString();

        return token;
    }

}
