package com.example.trip_planner.member.request.signIn;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignInRequest {

    private String userId;
    private String password;

}
