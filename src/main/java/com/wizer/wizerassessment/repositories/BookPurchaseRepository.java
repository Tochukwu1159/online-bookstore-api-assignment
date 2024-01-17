package com.wizer.wizerassessment.repositories;

import com.wizer.wizerassessment.models.Book;
import com.wizer.wizerassessment.models.BookPurchase;
import com.wizer.wizerassessment.models.User;
import com.wizer.wizerassessment.utils.PurchaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookPurchaseRepository extends JpaRepository<BookPurchase, Long> {
    BookPurchase findByUserAndBookAndStatusNot(User user, Book book, PurchaseStatus returned);
}
