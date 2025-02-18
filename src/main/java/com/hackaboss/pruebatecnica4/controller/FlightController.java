package com.hackaboss.pruebatecnica4.controller;

import com.hackaboss.pruebatecnica4.dto.FlightDTO;
import com.hackaboss.pruebatecnica4.services.IFlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agency/flights")
public class FlightController {

    private final IFlightService flightService;

    public FlightController(IFlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public ResponseEntity<List<FlightDTO>> getAllFlights() {
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @GetMapping("/{flightNumber}")
    public ResponseEntity<FlightDTO> getFlightByNumber(@PathVariable String flightNumber) {
        return flightService.getFlightByNumber(flightNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/available")
    public ResponseEntity<List<FlightDTO>> getAvailableFlights(
            @RequestParam String dateFrom,
            @RequestParam String dateTo,
            @RequestParam String origin,
            @RequestParam String destination) {
        return ResponseEntity.ok(flightService.getAvailableFlights(dateFrom, dateTo, origin, destination));
    }

    @PostMapping("/new")
    public ResponseEntity<String> createFlight(@RequestBody FlightDTO flightDTO) {
        FlightDTO savedFlight = flightService.saveFlight(flightDTO);
        String mensaje = "Vuelo creado correctamente con número: " + savedFlight.getFlightNumber();
        return ResponseEntity.ok(mensaje);
    }

    @PutMapping("/edit/{flightNumber}")
    public ResponseEntity<String> updateFlight(@PathVariable String flightNumber, @RequestBody FlightDTO flightDTO) {
        FlightDTO updatedFlight = flightService.updateFlight(flightNumber, flightDTO);
        String mensaje = "Vuelo actualizado correctamente con número: " + updatedFlight.getFlightNumber();
        return ResponseEntity.ok(mensaje);
    }

    @DeleteMapping("/delete/{flightNumber}")
    public ResponseEntity<String> deleteFlight(@PathVariable String flightNumber) {
        flightService.deleteFlight(flightNumber);
        String mensaje = "Vuelo eliminado correctamente con número: " + flightNumber;
        return ResponseEntity.ok(mensaje);
    }
}
