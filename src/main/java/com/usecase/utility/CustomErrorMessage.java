package com.usecase.utility;

import lombok.Data;


@Data
public class CustomErrorMessage {
	
	private String errorMessage;
	
	public CustomErrorMessage(String theErrorMessage)
	{
		this.errorMessage = theErrorMessage;
	}
	

}
