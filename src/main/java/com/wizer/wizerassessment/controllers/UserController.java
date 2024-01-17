package com.wizer.wizerassessment.controllers;


import com.wizer.wizerassessment.payloads.requests.LoginRequest;
import com.wizer.wizerassessment.payloads.requests.UserRequestDto;
import com.wizer.wizerassessment.payloads.responses.LoginResponse;
import com.wizer.wizerassessment.payloads.responses.UserResponse;
import com.wizer.wizerassessment.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/v1/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/admin")
    public UserResponse createAdmin(@RequestBody @Valid UserRequestDto userRequest)  {
        return userService.createAdmin(userRequest);
    }
    @PostMapping("/buyer")
    public UserResponse createBuyer(@RequestBody @Valid UserRequestDto userRequest)  {
        return userService.createBuyer(userRequest);
    }

    @PostMapping("/loginUser")
    public LoginResponse loginUser(@RequestBody @Valid LoginRequest loginRequest) {
        return userService.loginUser(loginRequest);

    }
}
