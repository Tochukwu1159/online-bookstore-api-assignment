package com.wizer.wizerassessment.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserObj {
    private String firstName;
    private String lastName;

    private String email;
}
