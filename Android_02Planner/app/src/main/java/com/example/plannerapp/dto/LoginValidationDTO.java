package com.example.plannerapp.dto;

public class LoginValidationDTO {
    private int status;
    private LoginBadRequest errors;

    public LoginBadRequest getErrors() {
        return errors;
    }

    public void setErrors(LoginBadRequest errors) {
        this.errors = errors;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
