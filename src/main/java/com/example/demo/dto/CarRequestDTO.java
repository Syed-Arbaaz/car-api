package com.example.demo.dto;

import org.springframework.web.multipart.MultipartFile;

//import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CarRequestDTO {
    @NotBlank(message = "name is required")
      @Size(min = 2, max = 50)
    private String name;

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotNull(message = "Price is required")
    private Double price;

   
    private MultipartFile imageFile;
}
