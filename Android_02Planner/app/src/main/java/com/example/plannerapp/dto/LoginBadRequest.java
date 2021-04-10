package com.example.plannerapp.dto;

import com.google.gson.annotations.SerializedName;

public class LoginBadRequest {
    @SerializedName("Email")
    private String []email;
    @SerializedName("Password")
    private String []password;
    private String invalid;

    public String[] getEmail() {
        return email;
    }

    public void setEmail(String[] email) {
        this.email = email;
    }

    public String[] getPassword() {
        return password;
    }

    public void setPassword(String[] password) {
        this.password = password;
    }

    public String getInvalid() {
        return invalid;
    }

    public void setInvalid(String invalid) {
        this.invalid = invalid;
    }
}
