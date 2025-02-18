package com.hackaboss.pruebatecnica4.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightDTO {
    private String flightNumber;
    private String origin;
    private String destination;
    private String seatType;
    private Double price;
    private String departureDate;
    private String returnDate;
}
