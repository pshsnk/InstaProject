package com.example.inst.payload.request;


import com.example.inst.annotations.PasswordMatches;
import com.example.inst.annotations.ValidEmail;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class SignupRequest {
    @Email(message = "Should have a email format")
    @NotBlank(message = "Email is required")
    @ValidEmail
    private String email;
    @NotEmpty(message = "Not empty")
    private String firstname;
    @NotEmpty(message = "Not empty")
    private String lastname;
    @NotEmpty(message = "Not empty")
    private String username;
    @NotEmpty(message = "Not empty")
    @Size( min = 6)
    private String password;
    @NotEmpty(message = "Not empty")
    private String confirmPassword;


}
