package com.example.trip_planner.signUp.request;

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
