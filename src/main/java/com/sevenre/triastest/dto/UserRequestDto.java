package com.sevenre.triastest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by nikhilesh on 14/07/17.
 */
public class UserRequestDto {
    @JsonProperty("userEmail")
    @NotBlank(
            message = "User Email can\'t be empty"
    )
    private String userName;
    @JsonProperty("password")
    @NotBlank(
            message = "User Password can\'t be empty"
    )
    private String password;

    public UserRequestDto() {
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


