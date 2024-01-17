package com.wizer.wizerassessment.repositories;

import com.wizer.wizerassessment.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {


}
