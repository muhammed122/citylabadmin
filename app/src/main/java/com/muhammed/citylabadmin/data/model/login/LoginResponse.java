package com.muhammed.citylabadmin.data.model.login;

public class LoginResponse{

	private UserData data;
	private String message;
	private boolean status;

	public UserData getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}


}
