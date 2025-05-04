package com.example.trip_planner.member.request.signIn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponse {

    private String authToken;
    private String userId;
}
