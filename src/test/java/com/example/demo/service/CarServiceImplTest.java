package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dto.CarResponseDTO;
import com.example.demo.exception.CarNotFoundException;
import com.example.demo.model.Car;
import com.example.demo.repository.CarRepository;
import com.example.demo.service.impl.CarServiceImpl;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarRepository repo;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
void shouldReturnCarWhenIdExists() {

    Car car = new Car();

    car.setId(1);
    car.setName("BMW");
    car.setBrand("BMW");
    car.setPrice(50000);

    when(repo.findById(1))
            .thenReturn(Optional.of(car));

    CarResponseDTO result =
            carService.getCarById(1);

    assertNotNull(result);

    assertEquals(
            "BMW",
            result.getName()
    );

    verify(repo)
            .findById(1);
}

@Test
void shouldThrowExceptionWhenCarNotFound() {

    when(repo.findById(999))
            .thenReturn(Optional.empty());

    assertThrows(
            CarNotFoundException.class,
            () -> carService.getCarById(999)
    );

    verify(repo)
            .findById(999);
}

}