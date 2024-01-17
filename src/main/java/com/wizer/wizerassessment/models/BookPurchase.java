package com.wizer.wizerassessment.models;

import com.wizer.wizerassessment.utils.PurchaseStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Book_purchase")
@Entity
@Builder
public class BookPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Assuming User is the entity representing a student

    private LocalDateTime boughtDate;

    private LocalDateTime returnDate;

    @Enumerated(EnumType.STRING)
    private PurchaseStatus status; // Enum for tracking if the book is SOLD or returned

    // Constructors, getters, setters, etc.
}
