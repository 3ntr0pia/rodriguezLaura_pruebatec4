package com.hackaboss.pruebatecnica4.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelDTO {
    @NotBlank(message = "El código del hotel no puede estar vacío")
    private String hotelCode;

    @NotBlank(message = "El nombre del hotel no puede estar vacío")
    private String name;

    @NotBlank(message = "La ciudad no puede estar vacía")
    private String city;

    @NotBlank(message = "El tipo de habitación no puede estar vacío")
    private String roomType;

    @Positive(message = "El precio por noche debe ser un número positivo")
    private Double pricePerNight;

    @NotBlank(message = "La fecha de disponibilidad 'desde' no puede estar vacía")
    private String availableFrom;

    @NotBlank(message = "La fecha de disponibilidad 'hasta' no puede estar vacía")
    private String availableTo;

    private Boolean isBooked;

}

