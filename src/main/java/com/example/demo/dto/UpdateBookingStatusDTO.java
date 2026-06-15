package com.example.demo.dto;

import com.example.demo.model.BookingStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBookingStatusDTO {
    private BookingStatus status;
}
