package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CarResponseDTO {
    private int id;
    private String name;
    private String brand;
    public CarResponseDTO(int id, String name, String brand) {
        this.id = id;
        this.name = name;
        this.brand = brand;
    }
    
}
