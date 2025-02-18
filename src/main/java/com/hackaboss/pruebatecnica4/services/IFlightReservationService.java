package com.hackaboss.pruebatecnica4.services;

import com.hackaboss.pruebatecnica4.dto.FlightReservationRequestDTO;
import com.hackaboss.pruebatecnica4.dto.FlightReservationResponseDTO;

import java.util.List;
import java.util.Optional;

public interface IFlightReservationService {
    List<FlightReservationResponseDTO> getAllReservations();
    Optional<FlightReservationResponseDTO> getReservationById(Long id);
    FlightReservationResponseDTO bookFlight(FlightReservationRequestDTO reservationRequest);
    void cancelReservation(Long id);
}