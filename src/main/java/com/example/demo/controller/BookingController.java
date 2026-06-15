package com.example.demo.controller;

// hisx nyub sgfl rrrw

import com.example.demo.repository.BookingRepository;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BookingRequestDTO;
import com.example.demo.dto.UpdateBookingStatusDTO;
import com.example.demo.model.Booking;
import com.example.demo.service.BookingService;
import com.example.demo.service.invoice.InvoiceService;


@RestController
@RequestMapping("/bookings")
@CrossOrigin("*")
public class BookingController {

    
    private final BookingRepository bookingRepository;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private InvoiceService invoiceService;

    BookingController(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @PostMapping
    public ResponseEntity<Booking> saveBooking(
        @RequestBody BookingRequestDTO dto
    ){
         System.out.println(dto.getCustomerName());
         System.out.println(dto.getCarName());

        return ResponseEntity.ok(bookingService.saveBooking(dto));
    }

        @GetMapping
        public ResponseEntity<List<Booking>> getAllBookings(){

        return ResponseEntity.ok(
            bookingRepository.findAll()
        );
        }

        @PutMapping("/{id}/status")
        public ResponseEntity<Booking> updateStatus(
            @PathVariable int id,
            @RequestBody UpdateBookingStatusDTO dto
        ){
            return ResponseEntity.ok(
                bookingService.updateStatus(id, dto.getStatus())
            );

        }

        @GetMapping("/{id}/invoice")
        public ResponseEntity<byte[]> downloadInvoice(@PathVariable int id){
        Booking booking = bookingRepository.findById(id).
        orElseThrow(()-> new RuntimeException("Booking not found")); 
        byte[] pdf = invoiceService.generateInvoice(booking);

        return ResponseEntity.ok()
        .header("content-type" , "application/pdf")
        .header("Content-Disposition", "attachment; filename=invoice_" + id + ".pdf")
        .body(pdf);
        }

        @GetMapping("/my-bookings")
        public ResponseEntity<List<Booking>> getMyBookings(Principal principal){
            String email = principal.getName();
            return ResponseEntity.ok(bookingService.findBookingsByEmail(email));
        }
       
}
