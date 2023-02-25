package com.example.inst.validations;

import com.example.inst.annotations.PasswordMatches;
import com.example.inst.payload.request.SignupRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {


    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        SignupRequest userSignUpRequest = (SignupRequest) value;
        return userSignUpRequest.getPassword().equals(userSignUpRequest.getConfirmPassword() );
    }
}
