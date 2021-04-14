package com.muhammed.citylabadmin.data.model.user;

import com.google.gson.annotations.SerializedName;

public class User {

	@SerializedName("phoneNumber")
	private String phoneNumber;

	@SerializedName("role")
	private Object role;

	@SerializedName("reservations")
	private Object reservations;

	@SerializedName("roleId")
	private int roleId;

	@SerializedName("labId")
	private int labId;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("lab")
	private Object lab;

	@SerializedName("results")
	private Object results;

	@SerializedName("token")
	private String token;

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public Object getRole(){
		return role;
	}

	public Object getReservations(){
		return reservations;
	}

	public int getRoleId(){
		return roleId;
	}

	public int getLabId(){
		return labId;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public Object getLab(){
		return lab;
	}

	public Object getResults(){
		return results;
	}

	public String getToken(){
		return token;
	}
}