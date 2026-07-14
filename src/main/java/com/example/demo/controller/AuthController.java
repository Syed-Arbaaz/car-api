package com.example.demo.controller;

import com.example.demo.service.UserService;
import com.example.demo.service.invoice.InvoiceService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AuthResponseDTO;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RefreshTokenRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.User;

//@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    AuthController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(
    @Valid @RequestBody RegisterRequest request) {
    return ResponseEntity.ok(userService.register(request));
    }


    @PostMapping("/login")
   public AuthResponseDTO login(@RequestBody LoginRequest request){
    return userService.login(request);
}

        @PostMapping("/refresh")
        public AuthResponseDTO refreshToken(
        @RequestBody RefreshTokenRequest request) {

        return userService.refreshToken(request);
        }

        @GetMapping("/verify")
        public ResponseEntity<String> verifyEmail(@RequestParam String token){
            return ResponseEntity.ok(userService.verifyEmail(token));
        }
}
