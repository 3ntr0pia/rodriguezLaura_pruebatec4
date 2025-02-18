USE travel_agency;

INSERT INTO hotels (hotel_code, name, city, room_type, price_per_night, available_from, available_to, is_booked) VALUES
                                                                                                                     ('H100', 'Hotel del Sol', 'Madrid', 'Double', 100.0, '2025-01-01', '2025-01-31', false),
                                                                                                                     ('H101', 'Hotel Europa', 'Barcelona', 'Single', 80.0, '2025-02-01', '2025-02-31', false);

INSERT INTO flights (flight_number, origin, destination, seat_type, price, departure_date, return_date) VALUES
                                                                                                            ('FL100', 'Madrid', 'Paris', 'Economy', 120.0, '2025-03-15', '2025-03-20'),
                                                                                                            ('FL101', 'Paris', 'Madrid', 'Economy', 110.0, '2025-03-20', '2025-03-25');

INSERT INTO flight_reservations (flight_number, booked_by, email, seat_count, status) VALUES
    ('FL100', 'Cliente', 'cliente@email.com', 2, 'CONFIRMED');

INSERT INTO hotel_reservations (hotel_code, check_in_date, check_out_date, guest_count, booked_by, email, status) VALUES
    ('H100', '2024-04-05', '2024-04-09', 2, 'Cliente', 'cliente@email.com', 'CONFIRMED');
