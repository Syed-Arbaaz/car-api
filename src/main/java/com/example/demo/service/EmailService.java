package com.example.demo.service;

public interface EmailService {

    void sendBookingStatusEmail(
            String to,
            String customerName,
            String carName,
            String status
    );

    void sendVerificationEmail(
        String to,
        String verificationLink
    );

    void sendInvoiceEmail(
        String to,
        byte[] pdfBytes,
        String fileName
    );
}