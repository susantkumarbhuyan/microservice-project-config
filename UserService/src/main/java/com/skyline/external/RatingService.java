package com.skyline.external;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.skyline.pozo.Rating;

@FeignClient(name = "RATINGSERVICE")
public interface RatingService {
	@GetMapping("/ratings/user/{userId}")
	List<Rating> getRating(@PathVariable long userId);
}
