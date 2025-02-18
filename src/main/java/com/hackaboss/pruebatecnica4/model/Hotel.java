package com.hackaboss.pruebatecnica4.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hotels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    @Id
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

    private Boolean isBooked = false;
}
