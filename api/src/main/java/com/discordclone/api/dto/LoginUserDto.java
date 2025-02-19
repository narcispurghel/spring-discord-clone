package com.discordclone.api.dto;

public class LoginUserDto {
    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public LoginUserDto setPassword(String password) {
        this.password = password;
        return this;
    }
}
