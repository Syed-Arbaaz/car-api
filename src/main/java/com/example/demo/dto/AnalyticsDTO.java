package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalyticsDTO {

    private long totalCars;
    private long totalUsers;
    private long totalBookings;

    private double totalRevenue;

    private long pendingBookings;
    private long confirmedBookings;
    private long deliveredBookings;
    private long cancelledBookings;
}