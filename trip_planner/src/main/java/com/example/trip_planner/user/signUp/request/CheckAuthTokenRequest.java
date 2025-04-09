package com.example.trip_planner.user.signUp.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CheckAuthTokenRequest {
    private String email;
    private String authToken;
}
