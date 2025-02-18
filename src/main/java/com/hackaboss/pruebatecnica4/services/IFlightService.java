package com.hackaboss.pruebatecnica4.services;

import com.hackaboss.pruebatecnica4.dto.FlightDTO;

import java.util.List;
import java.util.Optional;

public interface IFlightService {
    List<FlightDTO> getAllFlights();
    Optional<FlightDTO> getFlightByNumber(String flightNumber);
    List<FlightDTO> getAvailableFlights(String dateFrom, String dateTo, String origin, String destination);
    FlightDTO saveFlight(FlightDTO flightDTO);
    FlightDTO updateFlight(String flightNumber, FlightDTO flightDTO);
    void deleteFlight(String flightNumber);
}