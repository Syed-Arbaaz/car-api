package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AuthResponseDTO;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RefreshTokenRequest;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public User registerUser(User user){

    user.setPassword(
            passwordEncoder.encode(user.getPassword())
    );

    if(user.getRole() == null || user.getRole().isEmpty()){
        user.setRole("USER");
    }

    return repo.save(user);
}

    public AuthResponseDTO login(LoginRequest request){
            User user = repo.findByUsername(request.getUsername()).orElseThrow(()-> new RuntimeException("User Not found"));
            boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());
            if(!matches){
                throw new RuntimeException( "invalid passsword");
            }
            String accessToken = jwtService.generateAccessToken(user.getUsername(), user.getRole());

            String refreshToken = jwtService.generateRefreshToken(user.getUsername());
            
            return new AuthResponseDTO(accessToken, refreshToken);
    }

    public AuthResponseDTO refreshToken(RefreshTokenRequest request){
        String refreshToken = request.getRefreshToken();
        boolean valid = jwtService.validateToken(refreshToken);
        if(!valid){
            throw new RuntimeException("Invalid refresh token");
        }

        String username = jwtService.extractUsername(refreshToken);

        User user = repo.findByUsername(username)
        .orElseThrow(()-> new RuntimeException("User not found"));

        String newAccessToken = jwtService.generateAccessToken(user.getUsername(), user.getRole());
        
        return new AuthResponseDTO(newAccessToken, refreshToken);
    }

}
