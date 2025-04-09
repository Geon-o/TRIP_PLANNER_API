package com.example.trip_planner.user.signUp.auth.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmailAuthRequest {

    private String email;
    private int deadlineTime;
}
