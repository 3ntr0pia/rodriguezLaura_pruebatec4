package com.hackaboss.pruebatecnica4.dto;


import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class HotelReservationRequestDTO {
    private String dateFrom;
    private String dateTo;
    private String hotelCode;
    private Integer peopleQ;
    private String roomType;
    private List<String> guests;
}
