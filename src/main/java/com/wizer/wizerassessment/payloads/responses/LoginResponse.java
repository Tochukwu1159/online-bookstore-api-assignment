package com.wizer.wizerassessment.payloads.responses;

import com.wizer.wizerassessment.payloads.responses.UserObj;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private UserObj user;

    public LoginResponse(String token, UserObj user) {
        this.token = token;
        this.user = user;
    }

    // Getters and setters for token and user

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserObj getUser() {
        return user;
    }

    public void setUser(UserObj user) {
        this.user = user;
    }
}