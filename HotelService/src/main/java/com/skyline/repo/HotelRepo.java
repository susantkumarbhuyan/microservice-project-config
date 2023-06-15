package com.skyline.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skyline.pozo.Hotel;

public interface HotelRepo extends JpaRepository<Hotel, Long> {

}
