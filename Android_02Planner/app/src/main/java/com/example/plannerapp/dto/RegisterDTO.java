package com.example.plannerapp.dto;

public class RegisterDTO {

    private String userName;
    private String displayName;
    private String email;
    private String password;

    public RegisterDTO() {
    }

    public RegisterDTO(String email, String password,
                       String userName, String displayName) {
        this.userName = userName;
        this.displayName = displayName;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
