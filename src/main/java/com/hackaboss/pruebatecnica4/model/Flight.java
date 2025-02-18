package com.hackaboss.pruebatecnica4.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "flights")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Flight {
    @Id
    private String flightNumber;

    private String origin;
    private String destination;
    private String seatType;
    private Double price;

    private String departureDate;
    private String returnDate;
}
