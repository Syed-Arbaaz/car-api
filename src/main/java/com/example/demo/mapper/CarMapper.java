package com.example.demo.mapper;

import com.example.demo.dto.CarRequestDTO;
import com.example.demo.dto.CarResponseDTO;
import com.example.demo.model.Car;

public class CarMapper {
    
    public static Car toEntity(CarRequestDTO dto){
        Car car = new Car();
        car.setName(dto.getName());
        car.setBrand(dto.getBrand());
        car.setPrice(dto.getPrice());

        return car;
    }

    public static CarResponseDTO toDTO(Car car){
        CarResponseDTO dto = new CarResponseDTO();
        dto.setId(car.getId());
        dto.setName(car.getName());
        dto.setBrand(car.getBrand());
        dto.setPrice(car.getPrice());
        dto.setImageUrl(car.getImageUrl());
        return dto;
    }
}
