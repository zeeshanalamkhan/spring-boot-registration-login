package com.zeeshan.service;

import java.util.HashSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.zeeshan.dao.RoleRepository;
import com.zeeshan.dao.UserRepository;
import com.zeeshan.model.Role;
import com.zeeshan.model.User;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public void saveUser(User user) {

		logger.debug("saveUser() execution started with user ", user);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(new HashSet<Role>(roleRepository.findAll()));
		userRepository.save(user);
		logger.debug("saveUser() execution completed with user ", user);
	}

	@Override
	public User findByUserName(String userName) {

		logger.debug("findByUserName() execution started with userName: ", userName);
		User user1 = userRepository.findByUserName(userName);
		logger.debug("findByUserName() execution completed with user: ", user1);
		return user1;
	}

}
