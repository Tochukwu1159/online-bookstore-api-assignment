package com.wizer.wizerassessment;

import com.wizer.wizerassessment.controllers.UserController;
import com.wizer.wizerassessment.payloads.requests.LoginRequest;
import com.wizer.wizerassessment.payloads.requests.UserRequestDto;
import com.wizer.wizerassessment.payloads.responses.LoginResponse;
import com.wizer.wizerassessment.payloads.responses.UserObj;
import com.wizer.wizerassessment.payloads.responses.UserResponse;
import com.wizer.wizerassessment.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


// Mockito
import static org.mockito.Mockito.*;

@SpringBootTest
class WizerassessmentApplicationTests {

	@Test
	void contextLoads() {
	}
	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	@Test
	public void testCreateAdmin() {
		// Given
		UserRequestDto userRequest = new UserRequestDto();
		userRequest.setFirstName("John");
		userRequest.setLatName("Doe");
		userRequest.setEmail("john.doe@example.com");
		userRequest.setPassword("password");
		userRequest.setConfirmPassword("password");
		userRequest.setPhoneNumber("1234567890");

		UserResponse expectedResponse = new UserResponse();
		expectedResponse.setFirstName("John");
		expectedResponse.setLastName("Doe");
		expectedResponse.setEmail("john.doe@example.com");

		when(userService.createAdmin(any(UserRequestDto.class))).thenReturn(expectedResponse);

		// When
		UserResponse actualResponse = userController.createAdmin(userRequest);

		// Then
		assertNotNull(actualResponse);
		assertEquals(expectedResponse.getFirstName(), actualResponse.getFirstName());
		assertEquals(expectedResponse.getLastName(), actualResponse.getLastName());
		assertEquals(expectedResponse.getEmail(), actualResponse.getEmail());

		// Verify that the userService.createAdmin method is called with the correct argument
		verify(userService, times(1)).createAdmin(eq(userRequest));
	}
	@Test
	public void testCreateBuyer() {
		// Given
		UserRequestDto userRequest = new UserRequestDto();
		userRequest.setFirstName("Alice");
		userRequest.setLatName("Smith");
		userRequest.setEmail("alice.smith@example.com");
		userRequest.setPassword("password");
		userRequest.setConfirmPassword("password");
		userRequest.setPhoneNumber("9876543210");

		UserResponse expectedResponse = new UserResponse();
		expectedResponse.setFirstName("Alice");
		expectedResponse.setLastName("Smith");
		expectedResponse.setEmail("alice.smith@example.com");

		when(userService.createBuyer(any(UserRequestDto.class))).thenReturn(expectedResponse);

		// When
		UserResponse actualResponse = userController.createBuyer(userRequest);

		// Then
		assertNotNull(actualResponse);
		assertEquals(expectedResponse.getFirstName(), actualResponse.getFirstName());
		assertEquals(expectedResponse.getLastName(), actualResponse.getLastName());
		assertEquals(expectedResponse.getEmail(), actualResponse.getEmail());

		// Verify that the userService.createBuyer method is called with the correct argument
		verify(userService, times(1)).createBuyer(eq(userRequest));
	}

	@Test
	public void testLoginUser() {
		// Given
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail("user@example.com");
		loginRequest.setPassword("password");

		LoginResponse expectedResponse = new LoginResponse();
		expectedResponse.setToken("someAuthToken");
		expectedResponse.setUser(new UserObj("John", "Doe", "user@example.com"));

		when(userService.loginUser(any(LoginRequest.class))).thenReturn(expectedResponse);

		// When
		LoginResponse actualResponse = userController.loginUser(loginRequest);

		// Then
		assertNotNull(actualResponse);
		assertEquals(expectedResponse.getToken(), actualResponse.getToken());

		// Verify that the userService.loginUser method is called with the correct argument
		verify(userService, times(1)).loginUser(eq(loginRequest));
	}

}
