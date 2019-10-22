package com.zeeshan.service;

import com.zeeshan.model.User;

public interface UserService {

	public void saveUser(User user);

	public User findByUserName(String userName);
	
}
