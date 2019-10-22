package com.zeeshan.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

	private static final Logger logger = LogManager.getLogger(SecurityServiceImpl.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public String findLoggedInUser() {

		logger.info("findLoggedInUser");
		// Object userDetails =
		// SecurityContextHolder.getContext().getAuthentication().getDetails();
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userDetails instanceof UserDetails) {
			logger.debug("user is ", ((UserDetails) userDetails).getUsername());
			return ((UserDetails) userDetails).getUsername();
		}
		logger.debug("findLoggedInUser() no user found");
		return null;
	}

	@Override
	public void autoLogin(String userName, String password) {

		logger.info("autoLogin()");
		UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
		logger.debug("autoLogin()", userDetails);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, password, userDetails.getAuthorities());
		authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		if (usernamePasswordAuthenticationToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}

	}

}
