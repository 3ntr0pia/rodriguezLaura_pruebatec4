package com.hackaboss.pruebatecnica4.repository;

import com.hackaboss.pruebatecnica4.model.HotelReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelReservationRepository extends JpaRepository<HotelReservation, Long> {
    boolean existsByHotel_HotelCodeAndCheckInDateAndCheckOutDate(String hotelCode, String checkInDate, String checkOutDate);
}