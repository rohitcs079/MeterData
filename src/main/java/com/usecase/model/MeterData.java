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
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MeterData other = (MeterData) obj;
		if (meterId == null) {
			if (other.meterId != null)
				return false;
		} else if (!meterId.equals(other.meterId))
			return false;
		if (profile == null) {
			if (other.profile != null)
				return false;
		} else if (!profile.equals(other.profile))
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((meterId == null) ? 0 : meterId.hashCode());
		result = prime * result + ((profile == null) ? 0 : profile.hashCode());
		return result;
	}
	
	

}
