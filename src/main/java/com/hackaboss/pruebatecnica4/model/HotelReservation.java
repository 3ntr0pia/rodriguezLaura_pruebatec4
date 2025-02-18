package com.hackaboss.pruebatecnica4.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hotel_reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_code", nullable = false)
    private Hotel hotel;

    private String checkInDate;
    private String checkOutDate;
    private Integer guestCount;
    private String bookedBy;
    private String email;
    private String status;
}
