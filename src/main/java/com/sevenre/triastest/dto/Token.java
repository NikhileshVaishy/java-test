package com.sevenre.triastest.dto;

/**
 * Created by nikhilesh on 14/07/17.
 */
public class Token {
    private String token;
    private String payload;
    private UserDto authPayload;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public UserDto getAuthPayload() {
        return authPayload;
    }

    public void setAuthPayload(UserDto authPayload) {
        this.authPayload = authPayload;
    }
}
