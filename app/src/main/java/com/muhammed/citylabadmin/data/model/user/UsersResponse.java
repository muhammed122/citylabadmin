package com.muhammed.citylabadmin.data.model.user;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UsersResponse {

	@SerializedName("data")
	private List<User> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public List<User> getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}
}