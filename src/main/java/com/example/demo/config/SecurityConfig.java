package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.demo.filter.JwtFilter;
import org.springframework.http.HttpMethod;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

  @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .cors(cors ->{})
        .csrf(csrf -> csrf.disable())
        .httpBasic(httpBasic -> httpBasic.disable())
        .formLogin(form -> form.disable())

        .sessionManagement(session ->
        session.sessionCreationPolicy(
        SessionCreationPolicy.STATELESS
        )
        )

        .authorizeHttpRequests(auth -> auth

        // Allow browser CORS preflight
    .requestMatchers(HttpMethod.OPTIONS, "/**")
        .permitAll()

    // public auth APIs
    .requestMatchers("/auth/**")
        .permitAll()

        .requestMatchers("/uploads/**")
        .permitAll()

        .requestMatchers("/api/uploads/**").permitAll()

       

    // everyone can view cars
    .requestMatchers(HttpMethod.GET, "/cars/**")
        .permitAll()

    // Booking details in DB
    .requestMatchers(HttpMethod.POST, "/bookings/**")
    .permitAll()

    .requestMatchers(HttpMethod.GET, "/bookings/{id}/invoice")
    .permitAll()

    .requestMatchers(HttpMethod.GET, "/bookings/my-bookings")
    .permitAll()

    // Fetching Booking details for admin
    .requestMatchers(HttpMethod.GET, "/bookings/**")
    .hasRole("ADMIN")

    // Update Status only by admin
    .requestMatchers(HttpMethod.PUT, "/bookings/*/status")
    .hasRole("ADMIN")

    // only admin can manage cars
    .requestMatchers(HttpMethod.POST, "/cars/**")
        .hasRole("ADMIN")

    .requestMatchers(HttpMethod.PUT, "/cars/**")
        .hasRole("ADMIN")

    .requestMatchers(HttpMethod.DELETE, "/cars/**")
        .hasRole("ADMIN")

        //Analytics by only admin

    .requestMatchers("/analytics/**")
    .hasRole("ADMIN")


    //Springboot Actuator or metrics
    .requestMatchers("/actuator/**")
    .permitAll()

    .anyRequest().authenticated()
)

        .addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
        );

    return http.build();
}

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}



// previously used code for sec config

 /* .authorizeHttpRequests(auth -> auth

             .requestMatchers(
                "/auth/**",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html"
            ).permitAll()

             

            .authorizeHttpRequests(auth -> auth
    .requestMatchers("/auth/**").permitAll()

    .requestMatchers("/cars","/cars/**")
        .hasAnyRole("USER", "ADMIN")
    .requestMatchers("/car/**")
        .hasRole("ADMIN")

    .anyRequest().authenticated()
)       */


    