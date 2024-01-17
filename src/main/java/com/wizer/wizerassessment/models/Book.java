package com.wizer.wizerassessment.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.wizer.wizerassessment.utils.AvailabilityStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;



@Data
@Entity
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private Long isbn;
    private int quantityAvailable;
    private String availabilityStatus = "inStock";

}
