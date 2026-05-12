package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.response.ApiResponse;

public class CarNotFoundException extends RuntimeException{

    public CarNotFoundException(String message) {
        super(message);
    }
    
   @ExceptionHandler(CarNotFoundException.class)
public ResponseEntity<ApiResponse<String>> handleCarNotFound(CarNotFoundException ex) {

    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ApiResponse<>("error", ex.getMessage()));
}
}
