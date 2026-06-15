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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


@Service
public class CarServiceImpl implements CarService {
    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class); 
    @Autowired
    private CarRepository repo;

    
        @Override
public CarResponseDTO addCar(CarRequestDTO dto) {

    try {

        String fileName =
            UUID.randomUUID()
            + "_"
            + dto.getImageFile()
                 .getOriginalFilename();

        Path uploadPath =
            Paths.get("uploads");

        if(!Files.exists(uploadPath)){

            Files.createDirectories(
                uploadPath
            );
        }

        Files.copy(

            dto.getImageFile()
               .getInputStream(),

            uploadPath.resolve(fileName),

            StandardCopyOption.REPLACE_EXISTING
        );

        Car car =
            CarMapper.toEntity(dto);

        car.setImageUrl("http://localhost:8080/uploads/" + fileName);

        return CarMapper.toDTO(
            repo.save(car)
        );

    } catch(Exception e){

        throw new RuntimeException(
            "Image upload failed",
            e
        );
    }
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

    public CarResponseDTO updateCar(int id, CarRequestDTO dto){

        try{
    Car car = repo.findById(id)
            .orElseThrow(() ->
                    new RuntimeException("Car not found"));

                     // 1. DELETE OLD IMAGE
                    if (car.getImageUrl() != null) {

                    String oldFileName =
                car.getImageUrl()
                .substring(
                car.getImageUrl()
              .lastIndexOf("/") + 1
                );

                Path oldPath =
                Paths.get(
                "uploads",
                oldFileName
                );

            Files.deleteIfExists(oldPath);
        }

         // 2. UPLOAD NEW IMAGE
        String fileName =
                UUID.randomUUID()
                + "_"
                + dto.getImageFile().getOriginalFilename();

        Path uploadPath = Paths.get("uploads");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Files.copy(
                dto.getImageFile().getInputStream(),
                uploadPath.resolve(fileName),
                StandardCopyOption.REPLACE_EXISTING
        );


    car.setName(dto.getName());
    car.setBrand(dto.getBrand());
    car.setPrice(dto.getPrice());
    car.setImageUrl("/uploads/" + fileName);

    Car updated = repo.save(car);

    return CarMapper.toDTO(updated);
        }
        catch (Exception e) {
        throw new RuntimeException("Update failed", e);
    }

}

    @Override
public void deleteCar(int id) {

    Car car = repo.findById(id)
            .orElseThrow(() ->
                    new CarNotFoundException("Car not found with id: " + id)
            );

    // DELETE IMAGE FIRST
    if (car.getImageUrl() != null) {

        String fileName =
    car.getImageUrl()
       .substring(
           car.getImageUrl()
              .lastIndexOf("/") + 1
       );

Path path =
    Paths.get(
        "uploads",
        fileName
    );
        try {
            Files.deleteIfExists(path);
        } catch (Exception e) {
            System.out.println("Image delete failed: " + e.getMessage());
        }
    }

    repo.deleteById(id);
    }
}