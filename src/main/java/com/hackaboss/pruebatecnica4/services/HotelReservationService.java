package com.hackaboss.pruebatecnica4.services;


import com.hackaboss.pruebatecnica4.dto.HotelReservationRequestDTO;
import com.hackaboss.pruebatecnica4.dto.HotelReservationResponseDTO;
import com.hackaboss.pruebatecnica4.model.Hotel;
import com.hackaboss.pruebatecnica4.model.HotelReservation;
import com.hackaboss.pruebatecnica4.repository.HotelRepository;
import com.hackaboss.pruebatecnica4.repository.HotelReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelReservationService implements IHotelReservationService {

    private final HotelReservationRepository reservationRepository;
    private final HotelRepository hotelRepository;

    public HotelReservationService(HotelReservationRepository reservationRepository, HotelRepository hotelRepository) {
        this.reservationRepository = reservationRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<HotelReservationResponseDTO> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<HotelReservationResponseDTO> getReservationById(Long id) {
        return reservationRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public HotelReservationResponseDTO bookRoom(HotelReservationRequestDTO request) {
        Optional<Hotel> hotelOpt = hotelRepository.findById(request.getHotelCode());
        if (hotelOpt.isEmpty()) {
            throw new RuntimeException("Hotel no encontrado.");
        }

        // Verificar si ya hay una reserva para el hotel en las mismas fechas
        boolean exists = reservationRepository.existsByHotel_HotelCodeAndCheckInDateAndCheckOutDate(
                request.getHotelCode(),
                request.getDateFrom(),
                request.getDateTo()
        );
        if (exists) {
            throw new RuntimeException("El hotel ya está reservado en estas fechas.");
        }

        Hotel hotel = hotelOpt.get();

        // Supongamos que las fechas vienen en formato "dd/mm/aaaa"
        int dayFrom = Integer.parseInt(request.getDateFrom().split("/")[0]);
        int dayTo = Integer.parseInt(request.getDateTo().split("/")[0]);
        int nights = dayTo - dayFrom;

        double totalPrice = request.getPeopleQ() * hotel.getPricePerNight() * nights;

        HotelReservation reservation = new HotelReservation();
        reservation.setHotel(hotel);
        reservation.setCheckInDate(request.getDateFrom());
        reservation.setCheckOutDate(request.getDateTo());
        reservation.setGuestCount(request.getPeopleQ());
        reservation.setBookedBy("Cliente");
        reservation.setEmail("cliente@email.com");
        reservation.setStatus("CONFIRMED");

        reservationRepository.save(reservation);

        HotelReservationResponseDTO responseDTO = convertToDTO(reservation);
        responseDTO.setTotalPrice(totalPrice);
        return responseDTO;
    }

    @Override
    public void cancelReservation(Long id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Reserva no encontrada.");
        }
    }

    private HotelReservationResponseDTO convertToDTO(HotelReservation reservation) {
        HotelReservationResponseDTO dto = new HotelReservationResponseDTO();
        dto.setHotelCode(reservation.getHotel().getHotelCode());
        dto.setHotelName(reservation.getHotel().getName());
        dto.setCheckInDate(reservation.getCheckInDate());
        dto.setCheckOutDate(reservation.getCheckOutDate());
        dto.setGuestCount(reservation.getGuestCount());
        dto.setStatus(reservation.getStatus());

        try {
            String[] checkInParts = reservation.getCheckInDate().split("/");
            String[] checkOutParts = reservation.getCheckOutDate().split("/");
            int dayFrom = Integer.parseInt(checkInParts[0]);
            int dayTo = Integer.parseInt(checkOutParts[0]);
            int nights = dayTo - dayFrom;
            double totalPrice = reservation.getGuestCount() * reservation.getHotel().getPricePerNight() * nights;
            dto.setTotalPrice(totalPrice);
        } catch (Exception e) {
            dto.setTotalPrice(0.0);
        }

        return dto;
    }

}