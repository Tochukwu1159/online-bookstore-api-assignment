package com.wizer.wizerassessment.service.impl;

import com.wizer.wizerassessment.exceptions.CustomOutOfStock;
import com.wizer.wizerassessment.models.User;
import com.wizer.wizerassessment.Security.SecurityConfig;
import com.wizer.wizerassessment.exceptions.CustomInternalServerException;
import com.wizer.wizerassessment.exceptions.ResourceNotFoundException;
import com.wizer.wizerassessment.models.Book;
import com.wizer.wizerassessment.models.BookPurchase;
import com.wizer.wizerassessment.payloads.requests.BookRequestDto;
import com.wizer.wizerassessment.payloads.responses.BookResponseDto;
import com.wizer.wizerassessment.repositories.BookPurchaseRepository;
import com.wizer.wizerassessment.repositories.BookRepository;
import com.wizer.wizerassessment.repositories.UserRepository;
import com.wizer.wizerassessment.service.BookStoreService;
import com.wizer.wizerassessment.utils.PurchaseStatus;
import com.wizer.wizerassessment.utils.Roles;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookStoreServiceImpl implements BookStoreService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private  ModelMapper modelMapper;

    @Autowired
    private BookPurchaseRepository bookPurchaseRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public BookResponseDto addBook(BookRequestDto requestDto) {
        try {
            String email = SecurityConfig.getAuthenticatedUserEmail();
            User admin = userRepository.findByEmailAndRoles(email, Roles.ADMIN);

            if (admin == null) {
                throw new ResourceNotFoundException("Please login as an Admin");
            }

            Book book = modelMapper.map(requestDto, Book.class);
            Book savedBook = bookRepository.save(book);

            return modelMapper.map(savedBook, BookResponseDto.class);

        } catch (ResourceNotFoundException e) {
            throw e; // Re-throw the ResourceNotFoundException as is

        } catch (Exception e) {
            throw new CustomInternalServerException("Error adding book: " + e.getMessage());
        }
    }

    @Override
    public Book updateBookQuantity(Long bookId, int quantityToAdd) {
        try {
            String email = SecurityConfig.getAuthenticatedUserEmail();
            User admin = userRepository.findByEmailAndRoles(email, Roles.ADMIN);
            if (admin == null) {
                throw new ResourceNotFoundException("Please login as an Admin"); // Return unauthorized response for non-admin users
            }
            Optional<Book> optionalBook = bookRepository.findById(bookId);

            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();

                // Increase the quantity by the specified amount
                int currentQuantity = book.getQuantityAvailable();
                book.setQuantityAvailable(currentQuantity + quantityToAdd);

                // Save the updated book
                return bookRepository.save(book);
            } else {
                throw new CustomInternalServerException("Book not found with ID: " + bookId);
            }
        } catch (Exception e) {
            throw new CustomInternalServerException("Error updating book: " + e.getMessage());

        }
    }

    @Override
    public BookResponseDto editBook(Long id, BookRequestDto requestDto) {
        try {
            String email = SecurityConfig.getAuthenticatedUserEmail();
            User admin = userRepository.findByEmailAndRoles(email, Roles.ADMIN);

            if (admin == null) {
                throw new ResourceNotFoundException("Please login as an Admin");
            }

            Book book = bookRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

            // Update the book properties
            book.setIsbn(requestDto.getIsbn());
            book.setTitle(requestDto.getTitle());
            book.setAuthor(requestDto.getAuthor());

            // Save the updated book
            Book updatedBook = bookRepository.save(book);

            return modelMapper.map(updatedBook, BookResponseDto.class);

        } catch (ResourceNotFoundException e) {
            throw e; // Re-throw the ResourceNotFoundException as is

        } catch (Exception e) {
            throw new CustomInternalServerException("Error editing book: " + e.getMessage());
        }
    }


    @Override
    public void deleteBook(Long bookId) {
        try {
            String email = SecurityConfig.getAuthenticatedUserEmail();
            User admin = userRepository.findByEmailAndRoles(email, Roles.ADMIN);
            if (admin == null) {
                throw new ResourceNotFoundException("Please login as an Admin");
            }
            bookRepository.deleteById(bookId);
        } catch (Exception e) {
            throw new CustomInternalServerException("Error deleting book: " + e.getMessage());
        }
    }

    @Override
    public List<Book> getAllBooks() {
        String email = SecurityConfig.getAuthenticatedUserEmail();
        User admin = userRepository.findByEmailAndRoles(email, Roles.ADMIN);
        if (admin == null) {
            throw new ResourceNotFoundException("Please login as an Admin");
        }
        try {
            return bookRepository.findAll();
        } catch (Exception e) {
            throw new CustomInternalServerException("Error fetching all books: " + e.getMessage());
        }
    }


    @Override
    public Page<Book> listBooks(int pageNo, int pageSize, String sortBy) {
        String email = SecurityConfig.getAuthenticatedUserEmail();
        User admin = userRepository.findByEmailAndRoles(email, Roles.ADMIN);
        if (admin == null) {
            throw new ResourceNotFoundException("Please login as an Admin");
        }
        try {
            PageRequest paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
            return bookRepository.findAll(paging);
        } catch (Exception e) {
            throw new CustomInternalServerException("Error fetching all books: " + e.getMessage());
        }
    }

    @Override
    public BookPurchase buyBook(Long bookId) {
        try {
            String email = SecurityConfig.getAuthenticatedUserEmail();
            Optional<User> user = userRepository.findByEmail(email);
       User   loggedInUser = user.get();

            if (loggedInUser == null) {
                throw new ResourceNotFoundException("Please login to buy book");
            }

            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new CustomInternalServerException("Book not found"));
            if(book.getAvailabilityStatus() == "outOfStock"){
                throw new CustomOutOfStock("No available copies of the book");
            }

            // Check if there are available copies
            if (book.getQuantityAvailable() > 0) {
                // Decrement the quantity available
                book.setQuantityAvailable(book.getQuantityAvailable() - 1);

                // Update availabilityStatus
                if (book.getQuantityAvailable() == 0) {
                    book.setAvailabilityStatus("outOfStock");
                }

                // Save the updated book
                bookRepository.save(book);

                // Create a purchase entry
                BookPurchase bookPurchase = new BookPurchase();
                bookPurchase.setUser(loggedInUser);
                bookPurchase.setBook(book);
                bookPurchase.setBoughtDate(LocalDateTime.now());
                bookPurchase.setStatus(PurchaseStatus.SOLD);

                return bookPurchaseRepository.save(bookPurchase);
            } else {
                throw new CustomInternalServerException("No available copies of the book");
            }
        } catch (Exception e) {
            throw new CustomInternalServerException("Error buying book: " + e.getMessage());
        }
    }




    @Override
    public BookPurchase returnBook(Long bookId) {
        try {
            BookPurchase returnedBook = bookPurchaseRepository.findById(bookId)
                    .orElseThrow(() -> new CustomInternalServerException("Book entry with id not found"));

            // Check if the book is already returned
            if (returnedBook.getStatus() == PurchaseStatus.RETURNED) {
                throw new CustomInternalServerException("Book already returned");
            }

            // Increment the quantity available
            Book book = returnedBook.getBook();
            book.setQuantityAvailable(book.getQuantityAvailable() + 1);

            // Update the availability status
            if (book.getQuantityAvailable() > 0) {
                book.setAvailabilityStatus("inStock");
            }

            // Save the updated book
            bookRepository.save(book);

            // Update the borrowing entry
            returnedBook.setReturnDate(LocalDateTime.now());
            returnedBook.setStatus(PurchaseStatus.RETURNED);

            return bookPurchaseRepository.save(returnedBook);
        } catch (Exception e) {
            throw new CustomInternalServerException("Error returning book: " + e.getMessage());
        }
    }

}

