package com.hackaboss.pruebatecnica4.controller;

import com.hackaboss.pruebatecnica4.dto.FlightReservationRequestDTO;
import com.hackaboss.pruebatecnica4.dto.FlightReservationResponseDTO;
import com.hackaboss.pruebatecnica4.services.IFlightReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agency/flight-booking")
public class FlightReservationController {

    private final IFlightReservationService reservationService;

    public FlightReservationController(IFlightReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<FlightReservationResponseDTO>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightReservationResponseDTO> getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/new")
    public ResponseEntity<String> bookFlight(@RequestBody FlightReservationRequestDTO request) {
        FlightReservationResponseDTO response = reservationService.bookFlight(request);
        String mensaje = "Reserva de vuelo creada correctamente con código de vuelo: "
                + response.getFlightCode() + ". Monto total de la reserva: " + response.getTotalPrice();
        return ResponseEntity.ok(mensaje);
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<String> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        String mensaje = "Reserva de vuelo cancelada correctamente con ID: " + id;
        return ResponseEntity.ok(mensaje);
    }
}
