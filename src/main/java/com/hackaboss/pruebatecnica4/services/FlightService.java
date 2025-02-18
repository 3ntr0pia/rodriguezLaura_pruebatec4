package com.hackaboss.pruebatecnica4.services;

import com.hackaboss.pruebatecnica4.dto.FlightDTO;
import com.hackaboss.pruebatecnica4.model.Flight;
import com.hackaboss.pruebatecnica4.repository.FlightRepository;
import com.hackaboss.pruebatecnica4.repository.FlightReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightService implements IFlightService {

    private final FlightRepository flightRepository;
    private final FlightReservationRepository flightReservationRepository;

    public FlightService(FlightRepository flightRepository, FlightReservationRepository flightReservationRepository) {
        this.flightRepository = flightRepository;
        this.flightReservationRepository = flightReservationRepository;
    }

    @Override
    public List<FlightDTO> getAllFlights() {
        return flightRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FlightDTO> getFlightByNumber(String flightNumber) {
        return flightRepository.findById(flightNumber).map(this::convertToDTO);
    }

    @Override
    public List<FlightDTO> getAvailableFlights(String dateFrom, String dateTo, String origin, String destination) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDate departureDate = LocalDate.parse(dateFrom, formatter);
            LocalDate returnDate = LocalDate.parse(dateTo, formatter);

            List<FlightDTO> backFlights = flightRepository.findAll().stream()
                    .filter(flight -> flight.getOrigin().trim().equalsIgnoreCase(origin.trim())
                            && flight.getDestination().trim().equalsIgnoreCase(destination.trim())
                            && LocalDate.parse(flight.getDepartureDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals(departureDate))
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            List<FlightDTO> returnFlights = flightRepository.findAll().stream()
                    .filter(flight -> flight.getOrigin().trim().equalsIgnoreCase(destination.trim())  // Se invierten los valores
                            && flight.getDestination().trim().equalsIgnoreCase(origin.trim())
                            && LocalDate.parse(flight.getDepartureDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals(returnDate))
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            backFlights.addAll(returnFlights);

            return backFlights;

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use dd/MM/yyyy.", e);
        }
    }




    @Override
    public FlightDTO saveFlight(FlightDTO flightDTO) {
        if (flightRepository.existsById(flightDTO.getFlightNumber())) {
            throw new RuntimeException("El vuelo con el número " + flightDTO.getFlightNumber() + " ya existe.");
        }
        Flight flight = convertToEntity(flightDTO);
        return convertToDTO(flightRepository.save(flight));
    }


    @Override
    public FlightDTO updateFlight(String flightNumber, FlightDTO flightDTO) {
        Optional<Flight> existingFlight = flightRepository.findById(flightNumber);
        if (existingFlight.isPresent()) {
            Flight flight = convertToEntity(flightDTO);
            return convertToDTO(flightRepository.save(flight));
        }
        throw new RuntimeException("Vuelo no encontrado.");
    }

    @Override
    public void deleteFlight(String flightNumber) {
        boolean hasReservations = flightReservationRepository.existsByFlight_FlightNumber(flightNumber);
        if (hasReservations) {
            throw new RuntimeException("No se puede eliminar el vuelo " + flightNumber
                    + " porque tiene reservas asociadas. Primero cancele las reservas.");
        }

        if (flightRepository.existsById(flightNumber)) {
            flightRepository.deleteById(flightNumber);
        } else {
            throw new RuntimeException("Vuelo no encontrado.");
        }
    }

    private FlightDTO convertToDTO(Flight flight) {
        FlightDTO dto = new FlightDTO();
        dto.setFlightNumber(flight.getFlightNumber());
        dto.setOrigin(flight.getOrigin());
        dto.setDestination(flight.getDestination());
        dto.setSeatType(flight.getSeatType());
        dto.setPrice(flight.getPrice());
        dto.setDepartureDate(flight.getDepartureDate());
        dto.setReturnDate(flight.getReturnDate());
        return dto;
    }

    private Flight convertToEntity(FlightDTO dto) {
        Flight flight = new Flight();
        flight.setFlightNumber(dto.getFlightNumber());
        flight.setOrigin(dto.getOrigin());
        flight.setDestination(dto.getDestination());
        flight.setSeatType(dto.getSeatType());
        flight.setPrice(dto.getPrice());
        flight.setDepartureDate(dto.getDepartureDate());
        flight.setReturnDate(dto.getReturnDate());
        return flight;
    }
}
