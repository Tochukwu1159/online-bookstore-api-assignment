package com.wizer.wizerassessment.controllers;

import com.wizer.wizerassessment.models.Book;
import com.wizer.wizerassessment.models.BookPurchase;
import com.wizer.wizerassessment.payloads.requests.BookRequestDto;
import com.wizer.wizerassessment.payloads.responses.BookResponseDto;
import com.wizer.wizerassessment.service.BookStoreService;
import com.wizer.wizerassessment.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookstore")
public class BookStoreController {

    @Autowired
    private BookStoreService libraryService;

    @PostMapping("/addBook")
    public ResponseEntity<BookResponseDto> addBook(@RequestBody BookRequestDto book) {
        try {
            BookResponseDto addedBook = libraryService.addBook(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedBook);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/editBook/{bookId}")
    public ResponseEntity<BookResponseDto> editBook(@PathVariable Long bookId, @RequestBody BookRequestDto updatedBook) {
        try {
            BookResponseDto editedBook = libraryService.editBook(bookId, updatedBook);
            return ResponseEntity.ok(editedBook);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/deleteBook/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable Long bookId) {
            libraryService.deleteBook(bookId);
            return ResponseEntity.ok("Book deleted successfully");
    }

    @GetMapping("/books")
    public ResponseEntity<Page<Book>> getBooks(@RequestParam(defaultValue = Constants.PAGENO) Integer pageNo,
                                               @RequestParam(defaultValue = Constants.PAGESIZE) Integer pageSize,
                                               @RequestParam(defaultValue = "id") String sortBy) {
        Page<Book> pagedResult = libraryService.listBooks(pageNo, pageSize, sortBy);

        if(pagedResult.hasContent()) {
            return new ResponseEntity<>(pagedResult, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/buy")
    public ResponseEntity<BookPurchase> buyBook(@RequestParam Long bookId) {
            BookPurchase borrowing = libraryService.buyBook( bookId);
            return ResponseEntity.ok(borrowing);

    }

    @PostMapping("/returnBook/{bookId}")
    public ResponseEntity<BookPurchase> returnBook(@PathVariable Long bookId) {
            BookPurchase returnedBook = libraryService.returnBook(bookId);
            return ResponseEntity.ok(returnedBook);
    }

    @PutMapping("/updateBookQuantity/{bookId}")
    public ResponseEntity<Book> updateBookQuantity(
            @PathVariable Long bookId,
            @RequestParam int quantityToAdd) {
            Book updatedBook = libraryService.updateBookQuantity(bookId, quantityToAdd);
            return ResponseEntity.ok(updatedBook);
}}

