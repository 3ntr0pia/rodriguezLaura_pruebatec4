package com.hackaboss.pruebatecnica4.services;

import com.hackaboss.pruebatecnica4.dto.HotelReservationRequestDTO;
import com.hackaboss.pruebatecnica4.dto.HotelReservationResponseDTO;

import java.util.List;
import java.util.Optional;

public interface IHotelReservationService {
    List<HotelReservationResponseDTO> getAllReservations();
    Optional<HotelReservationResponseDTO> getReservationById(Long id);
    HotelReservationResponseDTO bookRoom(HotelReservationRequestDTO reservationRequest);
    void cancelReservation(Long id);
}

