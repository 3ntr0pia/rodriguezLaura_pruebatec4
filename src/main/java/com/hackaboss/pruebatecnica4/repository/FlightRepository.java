package com.hackaboss.pruebatecnica4.repository;

import com.hackaboss.pruebatecnica4.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {
}