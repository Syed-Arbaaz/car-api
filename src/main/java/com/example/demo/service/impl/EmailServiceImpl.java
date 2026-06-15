package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.demo.service.EmailService;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendBookingStatusEmail(
            String to,
            String customerName,
            String carName,
            String status
    ) {

         System.out.println("EMAIL METHOD CALLED");
         System.out.println("Sending email to: " + to);

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("Booking Status Updated");

        message.setText(
                "Hello " + customerName +
                "\n\nYour booking for " + carName +
                " is now " + status +
                ".\n\nThank you for choosing us."
        );

        mailSender.send(message);

        System.out.println("EMAIL SENT SUCCESSFULLY");
    }

    @Override
    public void sendVerificationEmail(String to, String verificationLink){

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("please verify your account");

        message.setText("Thank you for registering \n\n" + "Click on the link below to verify your account: \n\n" + verificationLink);
    
        mailSender.send(message);
        }

        @Override
        public void sendInvoiceEmail(String to, byte[] pdfBytes, String fileName){
                try{
                        MimeMessage mimeMessage = mailSender.createMimeMessage();
                        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
                        
                        helper.setTo(to);
                        helper.setSubject("Your Vehicle Invoice");
                        helper.setText("Thank you for your purchase. \n\n"+"Please find your attached invoice");

                        helper.addAttachment(fileName, new ByteArrayResource(pdfBytes));
                        mailSender.send(mimeMessage);

                        System.out.println("Invoice Email send");
                        
                }
                catch(Exception e){
                        throw new RuntimeException("Failed to send invoice email", e);
                }
        }
}