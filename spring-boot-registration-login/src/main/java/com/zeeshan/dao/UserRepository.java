package com.zeeshan.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zeeshan.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByUserName(String userName);
	
}
