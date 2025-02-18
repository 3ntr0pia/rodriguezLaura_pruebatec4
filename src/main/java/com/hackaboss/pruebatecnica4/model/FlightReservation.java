package com.hackaboss.pruebatecnica4.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "flight_reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "flight_number", nullable = false)
    private Flight flight;

    private String bookedBy;
    private String email;
    private Integer seatCount;
    private String status;
}