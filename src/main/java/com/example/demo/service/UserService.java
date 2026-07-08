package com.example.demo.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AuthResponseDTO;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RefreshTokenRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.User;
import com.example.demo.model.VerificationToken;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VerificationTokenRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private EmailService emailService;

    @Value("${app.base-url}")
    private String baseUrl;

   public User register(RegisterRequest request){

    if(repo.existsByEmail(request.getEmail())){
        throw new RuntimeException("Email already exists");
    }

    User user = new User();

    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setPhone(request.getPhone());
    user.setEmail(request.getEmail());

    user.setPassword(
            passwordEncoder.encode(request.getPassword())
    );

    user.setRole("USER");
    user.setVerified(false);

    if(request.getRole() == null || request.getRole().isEmpty()){
        user.setRole("USER");
    } else {
        user.setRole(request.getRole());
    }

    User savedUser = repo.save(user);
    String token = UUID.randomUUID().toString();

    VerificationToken verificationToken = new VerificationToken();

    verificationToken.setToken(token);
    verificationToken.setUserId(savedUser.getId());
    verificationTokenRepository.save(verificationToken);
    
    String verificationLink =
        baseUrl + "/auth/verify?token="
        + token;

        emailService.sendVerificationEmail(savedUser.getEmail(), verificationLink);
        return savedUser;
}

public String verifyEmail(String token){
    VerificationToken verificationToken = 
    verificationTokenRepository.findByToken(token).
        orElseThrow(()-> new RuntimeException ("Invalid Token"));

        User user = repo.findById(verificationToken.getUserId()).
        orElseThrow(()-> new RuntimeException("User not found"));

        if (user.getVerified()) {
        return "Already verified";
        }

        user.setVerified(true);
        repo.save(user);
        verificationTokenRepository.delete(verificationToken);
        return "Email verified Successfully";
}

    public AuthResponseDTO login(LoginRequest request){
            User user = repo.findByEmail(request.getEmail()).orElseThrow(()-> new RuntimeException("User Not found"));
            boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());
            
            if (!user.getVerified()) {
            throw new RuntimeException(
            "Please verify your email first"
            );
                }
            if(!matches){
                throw new RuntimeException( "invalid passsword");
            }
            String accessToken = jwtService.generateAccessToken(user.getEmail(), user.getRole());

            String refreshToken = jwtService.generateRefreshToken(user.getEmail());
            
            return new AuthResponseDTO(accessToken, refreshToken);
    }

    public AuthResponseDTO refreshToken(RefreshTokenRequest request){
        String refreshToken = request.getRefreshToken();
        boolean valid = jwtService.validateToken(refreshToken);
        if(!valid){
            throw new RuntimeException("Invalid refresh token");
        }

        String username = jwtService.extractUsername(refreshToken);

        User user = repo.findByEmail(username)
        .orElseThrow(()-> new RuntimeException("User not found"));

        String newAccessToken = jwtService.generateAccessToken(user.getEmail(), user.getRole());
        
        return new AuthResponseDTO(newAccessToken, refreshToken);
    }

}
