package com.hackaboss.pruebatecnica4.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightReservationResponseDTO {
    private String flightCode;
    private String origin;
    private String destination;
    private String departureDate;
    private Integer seatCount;
    private Double totalPrice;
    private String status;
}