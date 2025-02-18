package com.hackaboss.pruebatecnica4.services;


import com.hackaboss.pruebatecnica4.dto.HotelDTO;
import com.hackaboss.pruebatecnica4.repository.HotelRepository;
import com.hackaboss.pruebatecnica4.repository.HotelReservationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HotelServiceSimpleTest {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelReservationRepository hotelReservationRepository;

    //EVITAR ERRORES ENTRE PRUEBAS
    @BeforeEach
    public void setUp() {
        hotelReservationRepository.deleteAll();
        hotelRepository.deleteAll();
    }


    //CREAR HOTEL
    @Test
    public void saveHotelTestSuccess() {
        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setHotelCode("H100");
        hotelDTO.setName("Hotel del Sol");
        hotelDTO.setCity("Madrid");
        hotelDTO.setRoomType("Double");
        hotelDTO.setPricePerNight(100.0);
        hotelDTO.setAvailableFrom("01/01/2025");
        hotelDTO.setAvailableTo("31/12/2025");
        hotelDTO.setIsBooked(false);

        HotelDTO resultado = hotelService.saveHotel(hotelDTO);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("H100", resultado.getHotelCode());
        Assertions.assertEquals("Hotel del Sol", resultado.getName());
    }

    //DUPLICAR -> GENERAR ERROR
    @Test
    public void saveHotelTestDuplicateThrowsException() {
        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setHotelCode("H101");
        hotelDTO.setName("Hotel Europa");
        hotelDTO.setCity("Barcelona");
        hotelDTO.setRoomType("Single");
        hotelDTO.setPricePerNight(80.0);
        hotelDTO.setAvailableFrom("01/01/2025");
        hotelDTO.setAvailableTo("31/12/2025");
        hotelDTO.setIsBooked(false);

        hotelService.saveHotel(hotelDTO);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            hotelService.saveHotel(hotelDTO);
        });
        Assertions.assertEquals("El hotel con el código H101 ya existe.", exception.getMessage());
    }

    //ELIMINAR HOTEL
    @Test
    public void saveAndDeleteHotelTest() {
        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setHotelCode("H200");
        hotelDTO.setName("Hotel Temporal");
        hotelDTO.setCity("Sevilla");
        hotelDTO.setRoomType("Single");
        hotelDTO.setPricePerNight(120.0);
        hotelDTO.setAvailableFrom("01/01/2026");
        hotelDTO.setAvailableTo("31/12/2026");
        hotelDTO.setIsBooked(false);

        HotelDTO creado = hotelService.saveHotel(hotelDTO);

        Assertions.assertNotNull(creado);
        Assertions.assertEquals("H200", creado.getHotelCode());

        hotelService.deleteHotel("H200");

        Assertions.assertFalse(hotelRepository.existsById("H200"),
                "El hotel debería haberse eliminado, pero todavía existe.");
    }

}