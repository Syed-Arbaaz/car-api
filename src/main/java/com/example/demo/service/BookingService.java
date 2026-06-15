package com.example.demo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BookingRequestDTO;
import com.example.demo.model.Booking;
import com.example.demo.model.BookingStatus;
import com.example.demo.repository.BookingRepository;
import com.example.demo.service.invoice.InvoiceService;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private InvoiceService invoiceService;

    public Booking saveBooking(BookingRequestDTO dto){
        Booking booking = new Booking();
        booking.setCustomerName(dto.getCustomerName());
        booking.setCustomerEmail(dto.getCustomerEmail());
        booking.setCustomerPhone(dto.getCustomerPhone());
        booking.setCarName(dto.getCarName());
        booking.setBrand(dto.getBrand());
        booking.setPrice(dto.getPrice());
     // booking.setStatus("PENDING");

        System.out.println("STATUS BEFORE SAVE: " + booking.getStatus());

        System.out.println("CLASS: " + booking.getClass().getName());
        System.out.println("STATUS: " + booking.getStatus());
        return bookingRepository.save(booking);
        
    }

    public Booking updateStatus(int id, BookingStatus status){
        Booking booking = bookingRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("Booking not found"));
        booking.setStatus(status);

        Booking updatedBooking = bookingRepository.save(booking);

        System.out.println("STATUS UPDATED TO: " + status);

        emailService.sendBookingStatusEmail(
            booking.getCustomerEmail(), 
            booking.getCustomerName(), 
            booking.getCarName(),
             status.name());

             if(status == BookingStatus.DELIVERED){

             byte[] pdf =
             invoiceService.generateInvoice(
                updatedBooking
             );

            emailService.sendInvoiceEmail(
            updatedBooking.getCustomerEmail(),
            pdf,
            "invoice_" +
            updatedBooking.getId() +
            ".pdf"
            );
        }

        return updatedBooking;
    }

    public List<Booking> findBookingsByEmail(String email){
        return bookingRepository.findByCustomerEmail(email);
    }

}
