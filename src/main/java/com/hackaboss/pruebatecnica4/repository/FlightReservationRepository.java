package com.hackaboss.pruebatecnica4.repository;

import com.hackaboss.pruebatecnica4.model.FlightReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightReservationRepository extends JpaRepository<FlightReservation, Long> {
    boolean existsByFlight_FlightNumberAndBookedBy(String flightNumber, String bookedBy);
    boolean existsByFlight_FlightNumber(String flightNumber);
}
