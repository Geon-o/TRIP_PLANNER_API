package com.example.trip_planner.user.signUp.util;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthTokenIssuance {

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
