package com.wizer.wizerassessment.payloads.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequestDto
{
    private String firstName;
    private String latName;
    private String email;
    private String password;
    private String confirmPassword;
    private String phoneNumber;
}
