package com.example.inst.payload.request;


import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginRequest {

    @NotEmpty(message = "not empty")
    private String username;
    @NotEmpty(message = "not empty")
    private String password;
}
