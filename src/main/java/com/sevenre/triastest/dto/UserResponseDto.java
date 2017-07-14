package com.sevenre.triastest.dto;

/**
 * Created by nikhilesh on 14/07/17.
 */
public class UserResponseDto {
    private String token;

    public UserResponseDto() {
    }

    public UserResponseDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
