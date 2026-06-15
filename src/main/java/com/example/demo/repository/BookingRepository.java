package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Booking;
import com.example.demo.model.BookingStatus;

import java.util.List;


public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByCustomerEmail(String customerEmail);

    long countByStatus(BookingStatus status);
}
