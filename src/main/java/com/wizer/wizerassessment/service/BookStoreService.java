package com.wizer.wizerassessment.service;

import com.wizer.wizerassessment.models.Book;
import com.wizer.wizerassessment.models.BookPurchase;
import com.wizer.wizerassessment.payloads.requests.BookRequestDto;
import com.wizer.wizerassessment.payloads.responses.BookResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookStoreService {
    BookResponseDto addBook(BookRequestDto requestDto);
    Book updateBookQuantity(Long bookId, int quantityToAdd);

    BookResponseDto editBook(Long id,BookRequestDto requestDto);
    void deleteBook(Long bookId);

    List<Book> getAllBooks();

    BookPurchase buyBook(Long bookId);

    BookPurchase returnBook(Long bookId);
    Page<Book> listBooks(int pageNo, int pageSize, String sortBy);
}

