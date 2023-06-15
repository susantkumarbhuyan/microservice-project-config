package com.skyline.service;

import java.util.List;

import com.skyline.pozo.Hotel;

public interface IHotelService {
	Hotel create(Hotel hotel);

	List<Hotel> getAllHotel();

	Hotel getHotel(long hotelId);
}
