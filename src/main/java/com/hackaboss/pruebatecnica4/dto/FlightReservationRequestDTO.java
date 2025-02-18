package com.hackaboss.pruebatecnica4.dto;


import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class FlightReservationRequestDTO {
    private String date;
    private String origin;
    private String destination;
    private String flightCode;
    private Integer peopleQ;
    private String seatType;
    private List<String> passengers;
}
