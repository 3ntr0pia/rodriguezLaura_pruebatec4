package com.hackaboss.pruebatecnica4.repository;


import com.hackaboss.pruebatecnica4.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, String> {

}