package com.example.demo.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtFilter extends OncePerRequestFilter {
   
        private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class); 
    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                String path = request.getServletPath();

if(path.equals("/auth/login") ||
   path.equals("/auth/register")) {

    filterChain.doFilter(request, response);
    return;
}
                
            String authHeader = request.getHeader("Authorization");
            System.out.println("AUTH HEADER: " + authHeader);

            if(authHeader!=null && authHeader.startsWith("Bearer ")){
                String token = authHeader.substring(7);
                try{
                        String username = jwtService.extractUsername(token);

                String role = jwtService.extractRole(token);
                System.out.println("TOKEN: " + token);

        List<SimpleGrantedAuthority> authorities =
        List.of(
                new SimpleGrantedAuthority(
                        "ROLE_" + role
                )
        );

        UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(
                username,
                null,
                authorities
        );

        SecurityContextHolder.getContext()
        .setAuthentication(authToken);
        System.out.println("AUTHENTICATION SET");

        logger.info("User logged in: {}", username);
                   }
                catch(Exception e){
                        e.printStackTrace();
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }

                filterChain.doFilter(request, response);
        }  

        @Override
        protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getServletPath();

         return path.startsWith("/swagger-ui")
            || path.startsWith("/v3/api-docs")
            || path.startsWith("/auth")
            || path.startsWith("/uploads");
        }
}
