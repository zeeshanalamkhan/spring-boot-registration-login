package com.zeeshan.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.zeeshan.model.User;
import com.zeeshan.service.UserService;

@Component
public class UserValidator implements Validator {

	private static final Logger logger = LogManager.getLogger(UserValidator.class);

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {

		logger.info("supports()");
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {

		logger.info("validate() with Object", o);

		User user = (User) o;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "Not Empty");
		if (user.getUserName().length() < 6 || user.getUserName().length() > 32) {
			errors.rejectValue("userName", "Size.userForm.userName");
		}

		if (userService.findByUserName(user.getUserName()) != null) {
			errors.rejectValue("userName", "Duplicate.userForm.userName");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Not Empty");
		if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
			errors.rejectValue("password", "Size.userForm.password");
		}
		if (!user.getPassword().equals(user.getConfPassword())) {
			errors.rejectValue("confPassword", "Diff.userForm.confPassword");
		}
	}

}
