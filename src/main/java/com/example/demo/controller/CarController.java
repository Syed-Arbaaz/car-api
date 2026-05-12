package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.CarRequestDTO;
import com.example.demo.dto.CarResponseDTO;
//import com.example.demo.model.Car;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.CarService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class CarController {

    @Autowired
    private CarService carService;

   @GetMapping("/cars")
public ResponseEntity<ApiResponse<Page<CarResponseDTO>>> getAllCars(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "5") int size
) {

    ApiResponse<Page<CarResponseDTO>> response =
            new ApiResponse<>("success", carService.getCarsPaginated(page, size));

    return ResponseEntity.ok(response);
} 

    @PostMapping("/car")
    public ResponseEntity<ApiResponse<CarResponseDTO>> addCar(@Valid @RequestBody CarRequestDTO dto){
        ApiResponse<CarResponseDTO> response = new ApiResponse<>("Success", carService.addCar(dto));

         return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

   @GetMapping("/car/{id}")
public ResponseEntity<ApiResponse<CarResponseDTO>> getCarById(@PathVariable int id) {

    CarResponseDTO car = carService.getCarById(id);

    return ResponseEntity.ok(new ApiResponse<>("Success", car));
}

   /*  @PutMapping("/car/{id}")
    public Car updateCar(@PathVariable int id, @RequestBody Car updatedCar){
        return carService.updateCar(id,updatedCar);
    } */

    @DeleteMapping("/car/{id}")
    public ApiResponse<String> deleteCar(@PathVariable int id){
        carService.deleteCar(id);
       return new ApiResponse<>("Success", "Car delete successfully");
        
    } 

    @GetMapping("/cars/brand/{brand}")
    public ResponseEntity<ApiResponse<List<CarResponseDTO>>> getCarsByBrand(
        @PathVariable String brand){
            ApiResponse<List<CarResponseDTO>> response = new ApiResponse<>("Success", carService.getCarsByBrand(brand));
        return ResponseEntity.ok(response);
        }
}

