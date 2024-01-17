package com.wizer.wizerassessment.payloads.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookRequestDto {
    private String title;
    private String author;
    private Long isbn;

    private int quantityAvailable;
}
