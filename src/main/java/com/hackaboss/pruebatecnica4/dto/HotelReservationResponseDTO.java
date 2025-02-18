package com.hackaboss.pruebatecnica4.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelReservationResponseDTO {
    private String hotelCode;
    private String hotelName;
    private String checkInDate;
    private String checkOutDate;
    private Integer guestCount;
    private Double totalPrice;
    private String status;
}
