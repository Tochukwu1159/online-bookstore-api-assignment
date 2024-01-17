package com.wizer.wizerassessment.service;

import com.wizer.wizerassessment.models.User;
import com.wizer.wizerassessment.payloads.requests.LoginRequest;
import com.wizer.wizerassessment.payloads.requests.UserRequestDto;
import com.wizer.wizerassessment.payloads.responses.LoginResponse;
import com.wizer.wizerassessment.payloads.responses.UserResponse;

public interface UserService
{
    UserResponse createAdmin(UserRequestDto userRequest);
    UserResponse createBuyer(UserRequestDto userRequest);

    LoginResponse loginUser(LoginRequest loginRequest);
}
