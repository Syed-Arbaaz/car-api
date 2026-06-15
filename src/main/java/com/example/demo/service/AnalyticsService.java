package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AnalyticsDTO;
import com.example.demo.model.Booking;
import com.example.demo.model.BookingStatus;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.UserRepository;

@Service
public class AnalyticsService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    public AnalyticsDTO getDashboardStats(){
        AnalyticsDTO dto = new AnalyticsDTO();
        dto.setTotalCars(carRepository.count());
        dto.setTotalUsers(userRepository.count());
        dto.setTotalBookings(bookingRepository.count());

        dto.setPendingBookings(bookingRepository.countByStatus(BookingStatus.PENDING));
        dto.setConfirmedBookings(bookingRepository.countByStatus(BookingStatus.CONFIRMED));
        dto.setDeliveredBookings(bookingRepository.countByStatus(BookingStatus.DELIVERED));
        dto.setCancelledBookings(bookingRepository.countByStatus(BookingStatus.CANCELLED));

        double revenue = bookingRepository.findAll().stream()
        .filter(
            booking ->booking.getStatus()==BookingStatus.DELIVERED)
        .mapToDouble(Booking::getPrice)
        .sum();
        dto.setTotalRevenue(revenue);
        
        return dto;
    }
}
