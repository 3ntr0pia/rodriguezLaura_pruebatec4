package com.hackaboss.pruebatecnica4.controller;

import com.hackaboss.pruebatecnica4.dto.HotelReservationRequestDTO;
import com.hackaboss.pruebatecnica4.dto.HotelReservationResponseDTO;
import com.hackaboss.pruebatecnica4.services.IHotelReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agency/room-booking")
public class HotelReservationController {

    private final IHotelReservationService reservationService;

    public HotelReservationController(IHotelReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<HotelReservationResponseDTO>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelReservationResponseDTO> getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/new")
    public ResponseEntity<String> bookRoom(@RequestBody HotelReservationRequestDTO request) {
        HotelReservationResponseDTO response = reservationService.bookRoom(request);
        String mensaje = "Reserva de habitación creada correctamente para el hotel " + response.getHotelCode();
        return ResponseEntity.ok(mensaje);
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<String> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        String mensaje = "Reserva de habitación cancelada correctamente con ID: " + id;
        return ResponseEntity.ok(mensaje);
    }
}
