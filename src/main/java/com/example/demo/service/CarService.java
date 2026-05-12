package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.dto.CarRequestDTO;
import com.example.demo.dto.CarResponseDTO;

public interface CarService {

    CarResponseDTO addCar(CarRequestDTO dto);

    CarResponseDTO getCarById(int id);

    Page<CarResponseDTO> getCarsPaginated(int page, int size);

    List<CarResponseDTO> getCarsByBrand(String brand);

    void deleteCar(int id);
}

