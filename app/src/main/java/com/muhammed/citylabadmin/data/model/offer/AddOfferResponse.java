package com.muhammed.citylabadmin.data.model.offer;

public class AddOfferResponse{
	private Object data;
	private String message;
	private boolean status;

	public Object getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}
}
