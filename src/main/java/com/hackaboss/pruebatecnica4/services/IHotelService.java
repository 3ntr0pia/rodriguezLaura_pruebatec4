package com.hackaboss.pruebatecnica4.services;

import com.hackaboss.pruebatecnica4.dto.HotelDTO;

import java.util.List;
import java.util.Optional;

public interface IHotelService {
    List<HotelDTO> getAllHotels();
    Optional<HotelDTO> getHotelByCode(String hotelCode);
    List<HotelDTO> getAvailableHotels(String dateFrom, String dateTo, String city);
    HotelDTO saveHotel(HotelDTO hotelDTO);
    HotelDTO updateHotel(String hotelCode, HotelDTO hotelDTO);
    void deleteHotel(String hotelCode);
}