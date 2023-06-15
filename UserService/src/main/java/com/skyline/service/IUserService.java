package com.skyline.service;

import java.util.List;

import com.skyline.pozo.User;

public interface IUserService {
	User saveUser(User user);

	List<User> getAllUser();

	User getUser(long userId);
}
