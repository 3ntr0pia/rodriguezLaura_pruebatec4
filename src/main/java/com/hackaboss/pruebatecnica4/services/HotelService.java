package com.hackaboss.pruebatecnica4.services;

import com.hackaboss.pruebatecnica4.dto.HotelDTO;
import com.hackaboss.pruebatecnica4.model.Hotel;
import com.hackaboss.pruebatecnica4.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelService implements IHotelService {

    private final HotelRepository hotelRepository;

    HotelService(HotelRepository hotelRepository) { this.hotelRepository = hotelRepository; }

    @Override
    public List<HotelDTO> getAllHotels() {
        return hotelRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<HotelDTO> getHotelByCode(String hotelCode) {
        return hotelRepository.findById(hotelCode).map(this::convertToDTO);
    }

    @Override
    public List<HotelDTO> getAvailableHotels(String dateFrom, String dateTo, String city) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            LocalDate checkInDate = LocalDate.parse(dateFrom, formatter);
            LocalDate checkOutDate = LocalDate.parse(dateTo, formatter);
            List<HotelDTO> allHotels = hotelRepository.findAll().stream()
                        .filter(hotel -> {
                        LocalDate availableFrom = LocalDate.parse(hotel.getAvailableFrom(), formatter);
                        LocalDate availableTo = LocalDate.parse(hotel.getAvailableTo(), formatter);

                        boolean matchesCity = hotel.getCity().trim().equalsIgnoreCase(city.trim());
                        boolean available = !availableFrom.isAfter(checkOutDate) && !availableTo.isBefore(checkInDate);

                        return matchesCity && available;
                    })
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return allHotels;

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use yyyy-MM-dd.", e);
        }
    }



    @Override
    public HotelDTO saveHotel(HotelDTO hotelDTO) {
        if (hotelRepository.existsById(hotelDTO.getHotelCode())) {
            throw new RuntimeException("El hotel con el código " + hotelDTO.getHotelCode() + " ya existe.");
        }
        Hotel hotel = convertToEntity(hotelDTO);
        return convertToDTO(hotelRepository.save(hotel));
    }

    @Override
    public HotelDTO updateHotel(String hotelCode, HotelDTO hotelDTO) {
        if (hotelRepository.existsById(hotelCode)) {
            Hotel hotel = convertToEntity(hotelDTO);
            return convertToDTO(hotelRepository.save(hotel));
        }
        throw new RuntimeException("Hotel no encontrado.");
    }

    @Override
    public void deleteHotel(String hotelCode) {
        if (hotelRepository.existsById(hotelCode)) {
            hotelRepository.deleteById(hotelCode);
        } else {
            throw new RuntimeException("Hotel no encontrado.");
        }
    }

    // Métodos de conversión
    private HotelDTO convertToDTO(Hotel hotel) {
        HotelDTO dto = new HotelDTO();
        dto.setHotelCode(hotel.getHotelCode());
        dto.setName(hotel.getName());
        dto.setCity(hotel.getCity());
        dto.setRoomType(hotel.getRoomType());
        dto.setPricePerNight(hotel.getPricePerNight());
        dto.setAvailableFrom(hotel.getAvailableFrom());
        dto.setAvailableTo(hotel.getAvailableTo());
        dto.setIsBooked(hotel.getIsBooked());
        return dto;
    }

    private Hotel convertToEntity(HotelDTO dto) {
        Hotel hotel = new Hotel();
        hotel.setHotelCode(dto.getHotelCode());
        hotel.setName(dto.getName());
        hotel.setCity(dto.getCity());
        hotel.setRoomType(dto.getRoomType());
        hotel.setPricePerNight(dto.getPricePerNight());
        hotel.setAvailableFrom(dto.getAvailableFrom());
        hotel.setAvailableTo(dto.getAvailableTo());
        hotel.setIsBooked(dto.getIsBooked());
        return hotel;
    }
}
