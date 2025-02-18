package com.hackaboss.pruebatecnica4.services;

import com.hackaboss.pruebatecnica4.dto.FlightReservationRequestDTO;
import com.hackaboss.pruebatecnica4.dto.FlightReservationResponseDTO;
import com.hackaboss.pruebatecnica4.model.Flight;
import com.hackaboss.pruebatecnica4.model.FlightReservation;
import com.hackaboss.pruebatecnica4.repository.FlightRepository;
import com.hackaboss.pruebatecnica4.repository.FlightReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightReservationService implements IFlightReservationService {

    private final FlightReservationRepository reservationRepository;
    private final FlightRepository flightRepository;

    public FlightReservationService(FlightReservationRepository reservationRepository, FlightRepository flightRepository) {
        this.reservationRepository = reservationRepository;
        this.flightRepository = flightRepository;
    }

    @Override
    public List<FlightReservationResponseDTO> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FlightReservationResponseDTO> getReservationById(Long id) {
        return reservationRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public FlightReservationResponseDTO bookFlight(FlightReservationRequestDTO request) {
        Optional<Flight> flightOpt = flightRepository.findById(request.getFlightCode());
        if (flightOpt.isEmpty()) {
            throw new RuntimeException("Vuelo no encontrado.");
        }
        String bookedBy = "Cliente";
        boolean exists = reservationRepository.existsByFlight_FlightNumberAndBookedBy(request.getFlightCode(), bookedBy);
        if (exists) {
            throw new RuntimeException("Ya existe una reserva para este vuelo.");
        }

        Flight flight = flightOpt.get();
        double totalPrice = request.getPeopleQ() * flight.getPrice();

        FlightReservation reservation = new FlightReservation();
        reservation.setFlight(flight);
        reservation.setBookedBy(bookedBy);
        reservation.setEmail("cliente@email.com");
        reservation.setSeatCount(request.getPeopleQ());
        reservation.setStatus("CONFIRMED");

        reservationRepository.save(reservation);
        return convertToDTO(reservation);
    }

    @Override
    public void cancelReservation(Long id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Reserva no encontrada.");
        }
    }

    private FlightReservationResponseDTO convertToDTO(FlightReservation reservation) {
        FlightReservationResponseDTO dto = new FlightReservationResponseDTO();
        dto.setFlightCode(reservation.getFlight().getFlightNumber());
        dto.setOrigin(reservation.getFlight().getOrigin());
        dto.setDestination(reservation.getFlight().getDestination());
        dto.setDepartureDate(reservation.getFlight().getDepartureDate());
        dto.setSeatCount(reservation.getSeatCount());
        dto.setTotalPrice(reservation.getSeatCount() * reservation.getFlight().getPrice());
        dto.setStatus(reservation.getStatus());
        return dto;
    }
}