package com.wizer.wizerassessment.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookResponseDto {
    private String title;
    private String author;
    private Long isbn;
    private int quantityAvailable;
}
