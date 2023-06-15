 package com.skyline.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skyline.pozo.User;

public interface UserRepo extends JpaRepository<User, Long>{

}
