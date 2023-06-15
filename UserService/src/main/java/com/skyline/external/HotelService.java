package com.skyline.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.skyline.pozo.Hotel;

@FeignClient(name = "HOTELSERVICE")
public interface HotelService {
	@GetMapping("/hotels/{hotelId}")
	Hotel getHotel(@PathVariable long hotelId);
}
