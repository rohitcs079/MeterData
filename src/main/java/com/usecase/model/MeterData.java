package com.usecase.model;

import lombok.Data;


@Data
public class MeterData {
	
	public MeterData(String meterId2, String profile2) {
		this.meterId=meterId2;
		this.profile=profile2;
	}
	private String profile;
	private String meterId;
	public MeterData() {
		super();
	}
	
	

}
