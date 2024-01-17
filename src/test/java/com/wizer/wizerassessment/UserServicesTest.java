package com.wizer.wizerassessment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.wizer.wizerassessment.exceptions.BadRequestException;
import com.wizer.wizerassessment.exceptions.ResourceNotFoundException;
import com.wizer.wizerassessment.exceptions.UserPasswordMismatchException;
import com.wizer.wizerassessment.models.User;
import com.wizer.wizerassessment.payloads.requests.UserRequestDto;
import com.wizer.wizerassessment.payloads.responses.UserResponse;
import com.wizer.wizerassessment.repositories.UserRepository;
import com.wizer.wizerassessment.service.UserService;
import com.wizer.wizerassessment.service.impl.BookStoreServiceImpl;
import com.wizer.wizerassessment.service.impl.UserServiceImpl;
import com.wizer.wizerassessment.utils.Roles;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class UserServicesTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void createAdmin_Success() {
        UserRequestDto userRequestDto = new UserRequestDto("John", "Doe", "john.doe@example.com", "password123", "password123", "1234567890");
        User savedUser = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("hashedPassword") // replace with the actual hashed password
                .isVerified(true)
                .phoneNumber("1234567890")
                .roles(Roles.ADMIN)
                .build();

        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.<User>empty());
        Mockito.when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");
        Mockito.when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponse result = userService.createAdmin(userRequestDto);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());

        // Verify that the save method was called with the correct user object
        Mockito.verify(userRepository, Mockito.times(1)).save(any(User.class));
    }

    @Test
    void createAdmin_EmailAlreadyExists() {
        UserRequestDto userRequestDto = new UserRequestDto("John", "Doe", "john.doe@example.com", "password123", "password123", "1234567890");

        // Simulate that the email already exists in the database
        Mockito.when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(new User()));

        // Verify that a ResourceNotFoundException is thrown
        assertThrows(ResourceNotFoundException.class, () -> userService.createAdmin(userRequestDto));

        // Verify that the save method was not called
        Mockito.verify(userRepository, Mockito.never()).save(any(User.class));
    }

    @Test
    void createAdmin_PasswordMismatch() {
        UserRequestDto userRequestDto = new UserRequestDto("John", "Doe", "john.doe@example.com", "password123", "password456", "1234567890");

        // Verify that a UserPasswordMismatchException is thrown
        assertThrows(UserPasswordMismatchException.class, () -> userService.createAdmin(userRequestDto));

        // Verify that the save method was not called
        Mockito.verify(userRepository, Mockito.never()).save(any(User.class));
    }

    @Test
    void createAdmin_InvalidEmail() {
        UserRequestDto userRequestDto = new UserRequestDto("John", "Doe", "invalid.email", "password123", "password123", "1234567890");

        // Verify that a BadRequestException is thrown
        assertThrows(BadRequestException.class, () -> userService.createAdmin(userRequestDto));

        // Verify that the save method was not called
        Mockito.verify(userRepository, Mockito.never()).save(any(User.class));
    }

    @Test
    void createAdmin_ShortPassword() {
        UserRequestDto userRequestDto = new UserRequestDto("John", "Doe", "john.doe@example.com", "pass", "pass", "1234567890");

        // Verify that a BadRequestException is thrown
        assertThrows(BadRequestException.class, () -> userService.createAdmin(userRequestDto));

        // Verify that the save method was not called
        Mockito.verify(userRepository, Mockito.never()).save(any(User.class));
    }
}