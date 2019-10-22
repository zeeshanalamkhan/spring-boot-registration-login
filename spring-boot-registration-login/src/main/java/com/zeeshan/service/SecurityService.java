package com.zeeshan.service;

public interface SecurityService {

	public String findLoggedInUser();

	public void autoLogin(String userName, String password);

}
