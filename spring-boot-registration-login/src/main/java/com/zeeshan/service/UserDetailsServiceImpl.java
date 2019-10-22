package com.zeeshan.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zeeshan.dao.UserRepository;
import com.zeeshan.model.Role;
import com.zeeshan.model.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger logger = LogManager.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		logger.info("loadUserByUsername() execution started");

		User user = userRepository.findByUserName(username);

		logger.debug("loadUserByUsername() ", user);
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();

		for (Role role : user.getRoles()) {
			logger.debug("loadByUsername() role ", role);
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));

		}

		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				grantedAuthorities);

	}

}
