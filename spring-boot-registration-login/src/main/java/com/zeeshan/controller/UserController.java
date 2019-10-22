package com.zeeshan.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zeeshan.model.User;
import com.zeeshan.service.SecurityService;
import com.zeeshan.service.UserService;
import com.zeeshan.validator.UserValidator;

@Controller
public class UserController {

	private static final Logger logger = LogManager.getLogger(UserController.class);
	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserValidator userValidator;

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		logger.debug("register() - GET execution started");
		model.addAttribute("userForm", new User());
		return "registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {

		logger.debug("registration() - POST execution started with user: ", userForm);
		userValidator.validate(userForm, bindingResult);
		if (bindingResult.hasErrors()) {
			return "registration";
		}
		logger.info("registration() - POST saveUser() execution started");
		userService.saveUser(userForm);
		logger.info("registration() - POST saveUser() execution completed with user: ", userForm);
		securityService.autoLogin(userForm.getUserName(), userForm.getPassword());
		return "redirect:/welcome";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {
		logger.info("login() execution started");
		if (error != null) {
			model.addAttribute("error", "Your Username and Password is invalid");
		}
		if (logout != null) {
			model.addAttribute("message", "You have been logout successfully");
		}
		return "login";
	}

	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	public String welcome(Model model) {
		logger.info("welcome() execution started");
		return "welcome";
	}
}
