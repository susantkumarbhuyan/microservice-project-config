package com.skyline.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stafs")
public class StafsController {
	@GetMapping
	public List<String> getStafs() {
		return Arrays.asList("Niki", "Johny", "Bikash");
	}
}
