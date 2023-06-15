package com.skyline.service;

import java.util.List;

import com.skyline.pozo.Rating;

public interface IRatingService {
	Rating createRating(Rating rating);

	List<Rating> getAllRating();

	Rating getSingleRating(long ratingId);

	List<Rating> getRatingsByUserId(long userId);

	List<Rating> getRatingsByHotelId(long hotelId);
}
