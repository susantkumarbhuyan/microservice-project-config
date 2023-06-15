package com.skyline.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyline.exception.ResourceNotFountException;
import com.skyline.pozo.Hotel;
import com.skyline.repo.HotelRepo;
import com.skyline.service.IHotelService;
@Service
public class HotelServiceImpl implements IHotelService {
@Autowired
	private HotelRepo hotelRepo;
	@Override
	public Hotel create(Hotel hotel) {
		// TODO Auto-generated method stub
		return hotelRepo.save(hotel);
	}

	@Override
	public List<Hotel> getAllHotel() {
		// TODO Auto-generated method stub
		return hotelRepo.findAll();
	}

	@Override
	public Hotel getHotel(long hotelId) {
		// TODO Auto-generated method stub
		return hotelRepo.findById(hotelId).orElseThrow(()-> new ResourceNotFountException("Hotel Id not Found :" + hotelId));
	}

}
