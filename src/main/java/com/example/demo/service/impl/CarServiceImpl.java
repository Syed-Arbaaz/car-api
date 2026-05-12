package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CarRequestDTO;
import com.example.demo.dto.CarResponseDTO;
import com.example.demo.exception.CarNotFoundException;
import com.example.demo.filter.JwtFilter;
import com.example.demo.mapper.CarMapper;
import com.example.demo.model.Car;
import com.example.demo.repository.CarRepository;
import com.example.demo.service.CarService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CarServiceImpl implements CarService {
    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class); 
    @Autowired
    private CarRepository repo;

    @Override
    public CarResponseDTO addCar(CarRequestDTO dto) {
        Car car = CarMapper.toEntity(dto);
        return CarMapper.toDTO(repo.save(car));
    }

   @Override
public CarResponseDTO getCarById(int id) {

    logger.info("Fetching car with id: {}", id);

    Car car = repo.findById(id)
            .orElseThrow(() -> {
                logger.error("Car not found with id: {}", id);
                return new CarNotFoundException("Car not found with id: " + id);
            });

    return CarMapper.toDTO(car);
}

    @Override
    public Page<CarResponseDTO> getCarsPaginated(int page, int size) {
        return repo.findAll(PageRequest.of(page, size))
                .map(CarMapper::toDTO);
    }

    @Override
    public List<CarResponseDTO> getCarsByBrand(String brand) {
        return repo.findByBrand(brand)
                .stream()
                .map(CarMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCar(int id) {
        if (!repo.existsById(id)) {
            throw new CarNotFoundException("Car not found with id: "+id);
        }
        repo.deleteById(id);
    }
}