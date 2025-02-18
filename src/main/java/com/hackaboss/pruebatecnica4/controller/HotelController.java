package com.hackaboss.pruebatecnica4.controller;

import com.hackaboss.pruebatecnica4.dto.HotelDTO;
import com.hackaboss.pruebatecnica4.services.IHotelService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agency/hotels")
public class HotelController {

    private final IHotelService hotelService;

    public HotelController(IHotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public ResponseEntity<List<HotelDTO>> getAllHotels() {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    @GetMapping("/{hotelCode}")
    public ResponseEntity<HotelDTO> getHotelByCode(@PathVariable String hotelCode) {
        return hotelService.getHotelByCode(hotelCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<HotelDTO>> getAvailableHotels(
            @RequestParam String dateFrom,
            @RequestParam String dateTo,
            @RequestParam String destination) {
        return ResponseEntity.ok(hotelService.getAvailableHotels(dateFrom, dateTo, destination));
    }

    @PostMapping("/new")
    public ResponseEntity<?> createHotel(@Valid @RequestBody HotelDTO hotelDTO) {
        HotelDTO savedHotel = hotelService.saveHotel(hotelDTO);
        String mensaje = "Hotel creado correctamente con código: " + savedHotel.getHotelCode();
        return ResponseEntity.ok(mensaje);
    }

    @PutMapping("/edit/{hotelCode}")
    public ResponseEntity<?> updateHotel(@PathVariable String hotelCode, @Valid @RequestBody HotelDTO hotelDTO) {
        HotelDTO updatedHotel = hotelService.updateHotel(hotelCode, hotelDTO);
        String mensaje = "Hotel actualizado correctamente con código: " + updatedHotel.getHotelCode();
        return ResponseEntity.ok(mensaje);
    }

    @DeleteMapping("/delete/{hotelCode}")
    public ResponseEntity<?> deleteHotel(@PathVariable String hotelCode) {
        hotelService.deleteHotel(hotelCode);
        String mensaje = "Hotel eliminado correctamente con código: " + hotelCode;
        return ResponseEntity.ok(mensaje);
    }

}
