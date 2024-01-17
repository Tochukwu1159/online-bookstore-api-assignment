package com.wizer.wizerassessment.services;
import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

import com.wizer.wizerassessment.Security.SecurityConfig;
import com.wizer.wizerassessment.models.Book;
import com.wizer.wizerassessment.models.BookPurchase;
import com.wizer.wizerassessment.models.User;
import com.wizer.wizerassessment.payloads.requests.BookRequestDto;
import com.wizer.wizerassessment.payloads.responses.BookResponseDto;
import com.wizer.wizerassessment.repositories.BookPurchaseRepository;
import com.wizer.wizerassessment.repositories.BookRepository;
import com.wizer.wizerassessment.repositories.UserRepository;
import com.wizer.wizerassessment.service.impl.BookStoreServiceImpl;
import com.wizer.wizerassessment.utils.PurchaseStatus;
import com.wizer.wizerassessment.utils.Roles;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
        import org.mockito.Mock;
        import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BookStoreService {

    @InjectMocks
    private BookStoreServiceImpl bookStoreService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private BookPurchaseRepository bookPurchaseRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void testAddBook() {
        // Mocking SecurityConfig.getAuthenticatedUserEmail()
        when(SecurityConfig.getAuthenticatedUserEmail()).thenReturn("admin@example.com");

        // Mocking userRepository.findByEmailAndRoles()
        when(userRepository.findByEmailAndRoles(anyString(), eq(Roles.ADMIN))).thenReturn(new User());

        // Mocking modelMapper.map()
        when(modelMapper.map(any(BookRequestDto.class), eq(Book.class))).thenReturn(new Book());

        // Mocking bookRepository.save()
        when(bookRepository.save(any(Book.class))).thenReturn(new Book());

        // Mocking modelMapper.map() for the response
        when(modelMapper.map(any(Book.class), eq(BookResponseDto.class))).thenReturn(new BookResponseDto());

        // Call the method
        BookRequestDto requestDto = new BookRequestDto();
        assertDoesNotThrow(() -> {
            BookResponseDto responseDto = bookStoreService.addBook(requestDto);

            // Add assertions for the responseDto if needed
            assertNotNull(responseDto);
        });

        // Verify interactions
        verify(userRepository, times(1)).findByEmailAndRoles(anyString(), eq(Roles.ADMIN));
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(modelMapper, times(1)).map(any(Book.class), eq(BookResponseDto.class));
    }

    @Test
    void testUpdateBookQuantity() {
        // Mocking SecurityConfig.getAuthenticatedUserEmail()
        when(SecurityConfig.getAuthenticatedUserEmail()).thenReturn("admin@example.com");

        // Mocking userRepository.findByEmailAndRoles()
        when(userRepository.findByEmailAndRoles(anyString(), eq(Roles.ADMIN))).thenReturn(new User());

        // Mocking bookRepository.findById()
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(new Book()));

        // Mocking bookRepository.save()
        when(bookRepository.save(any(Book.class))).thenReturn(new Book());

        // Call the method
        Long bookId = 1L;
        int quantityToAdd = 5;

        assertDoesNotThrow(() -> {
            Book updatedBook = bookStoreService.updateBookQuantity(bookId, quantityToAdd);

            // Add assertions for the updatedBook if needed
            assertNotNull(updatedBook);
            assertEquals(5, updatedBook.getQuantityAvailable());
        });

        // Verify interactions
        verify(userRepository, times(1)).findByEmailAndRoles(anyString(), eq(Roles.ADMIN));
        verify(bookRepository, times(1)).findById(eq(bookId));
        verify(bookRepository, times(1)).save(any(Book.class));
    }
    @Test
    void testEditBook() {
        // Mocking SecurityConfig.getAuthenticatedUserEmail()
        when(SecurityConfig.getAuthenticatedUserEmail()).thenReturn("admin@example.com");

        // Mocking userRepository.findByEmailAndRoles()
        when(userRepository.findByEmailAndRoles(anyString(), eq(Roles.ADMIN))).thenReturn(new User());

        // Mocking bookRepository.findById()
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(new Book()));

        // Mocking modelMapper.map()
        when(modelMapper.map(any(Book.class), eq(BookResponseDto.class))).thenReturn(new BookResponseDto());

        // Mocking bookRepository.save()
        when(bookRepository.save(any(Book.class))).thenReturn(new Book());

        // Call the method
        Long bookId = 1L;
        BookRequestDto requestDto = new BookRequestDto();

        assertDoesNotThrow(() -> {
            BookResponseDto responseDto = bookStoreService.editBook(bookId, requestDto);

            // Add assertions for the responseDto if needed
            assertNotNull(responseDto);
        });

        // Verify interactions
        verify(userRepository, times(1)).findByEmailAndRoles(anyString(), eq(Roles.ADMIN));
        verify(bookRepository, times(1)).findById(eq(bookId));
        verify(modelMapper, times(1)).map(any(Book.class), eq(BookResponseDto.class));
        verify(bookRepository, times(1)).save(any(Book.class));
    }


//    @Test
//    void testDeleteBook() {
//        // Mocking userRepository.findByEmailAndRoles()
//        when(userRepository.findByEmailAndRoles(anyString(), eq(Roles.ADMIN))).thenReturn(Optional.of(new User()));
//
//        // Mocking bookRepository.deleteById()
//        doNothing().when(bookRepository).deleteById(anyLong());
//
//        // Call the method
//        Long bookId = 1L;
//
//        assertDoesNotThrow(() -> {
//            bookStoreService.deleteBook(bookId);
//        });
//
//        // Verify interactions
//        verify(userRepository, times(1)).findByEmailAndRoles(anyString(), eq(Roles.ADMIN));
//        verify(bookRepository, times(1)).deleteById(eq(bookId));
//    }
    @Test
    void testListBooks() {
        // Mocking SecurityConfig.getAuthenticatedUserEmail()
        when(SecurityConfig.getAuthenticatedUserEmail()).thenReturn("admin@example.com");

        // Mocking userRepository.findByEmailAndRoles()
        when(userRepository.findByEmailAndRoles(anyString(), eq(Roles.ADMIN))).thenReturn(new User());

        // Mocking bookRepository.findAll()
        Page<Book> emptyPage = new PageImpl<>(Collections.emptyList());
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("title"));
        when(bookRepository.findAll(pageRequest)).thenReturn(emptyPage);

        // Call the method
        assertDoesNotThrow(() -> {
            Page<Book> bookPage = bookStoreService.listBooks(0, 10, "title");

            // Add assertions for the returned bookPage if needed
            assertNotNull(bookPage);
            assertTrue(bookPage.isEmpty());
        });

        // Verify interactions
        verify(userRepository, times(1)).findByEmailAndRoles(anyString(), eq(Roles.ADMIN));
        verify(bookRepository, times(1)).findAll(pageRequest);
    }

    @Test
    void testBuyBook() {
        // Mocking SecurityConfig.getAuthenticatedUserEmail()
        when(SecurityConfig.getAuthenticatedUserEmail()).thenReturn("user@example.com");

        // Mocking userRepository.findByEmail()
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        // Mocking bookRepository.findById()
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(new Book()));

        // Mocking bookRepository.save()
        when(bookRepository.save(any(Book.class))).thenReturn(new Book());

        // Mocking bookPurchaseRepository.save()
        when(bookPurchaseRepository.save(any(BookPurchase.class))).thenReturn(new BookPurchase());

        // Call the method
        Long bookId = 1L;

        assertDoesNotThrow(() -> {
            BookPurchase bookPurchase = bookStoreService.buyBook(bookId);

            // Add assertions for the bookPurchase if needed
            assertNotNull(bookPurchase);
            assertEquals(PurchaseStatus.SOLD, bookPurchase.getStatus());
        });

        // Verify interactions
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(bookRepository, times(1)).findById(eq(bookId));
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(bookPurchaseRepository, times(1)).save(any(BookPurchase.class));
    }

    @Test
    void testReturnBook() {
        // Mocking bookPurchaseRepository.findById()
        when(bookPurchaseRepository.findById(anyLong())).thenReturn(Optional.of(new BookPurchase()));

        // Mocking bookRepository.findById()
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(new Book())); // Adjust this line

        // Mocking bookRepository.save()
        when(bookRepository.save(any(Book.class))).thenReturn(new Book());

        // Mocking bookPurchaseRepository.save()
        when(bookPurchaseRepository.save(any(BookPurchase.class))).thenReturn(new BookPurchase());

        // Call the method
        Long bookPurchaseId = 1L;

        assertDoesNotThrow(() -> {
            BookPurchase returnedBook = bookStoreService.returnBook(bookPurchaseId);

            // Add assertions for the returnedBook if needed
            assertNotNull(returnedBook);
            assertEquals(PurchaseStatus.RETURNED, returnedBook.getStatus());
        });

        // Verify interactions
        verify(bookPurchaseRepository, times(1)).findById(eq(bookPurchaseId));
        verify(bookRepository, times(1)).findById(anyLong()); // Verify that findById is called with any Long
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(bookPurchaseRepository, times(1)).save(any(BookPurchase.class));
    }
}


