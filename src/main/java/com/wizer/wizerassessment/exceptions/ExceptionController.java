package com.wizer.wizerassessment.exceptions;
import com.wizer.wizerassessment.payloads.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionController  {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(new ApiResponse<>("validation errors",false, errors), HttpStatus.BAD_REQUEST);
    }


    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("Errors", errors);
        return errorResponse;

    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> ResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ApiResponse<>((ex.getMessage()),false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomInternalServerException.class)
    public ResponseEntity<?> CustomInternalServerException(CustomInternalServerException ex) {
        return new ResponseEntity<>(new ApiResponse<>((ex.getMessage()),false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserPasswordMismatchException.class)
    public ResponseEntity<?> UserPasswordMismatchException(UserPasswordMismatchException ex) {
        return new ResponseEntity<>(new ApiResponse<>((ex.getMessage()),false), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<?> AuthenticationFailedException(AuthenticationFailedException ex) {
        return new ResponseEntity<>(new ApiResponse<>((ex.getMessage()),false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomOutOfStock.class)
    public ResponseEntity<?> CustomOutOfStock(CustomOutOfStock ex) {
        return new ResponseEntity<>(new ApiResponse<>((ex.getMessage()),false), HttpStatus.NOT_FOUND);
    }
}
