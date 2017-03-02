package com.usecase.model;

import org.mockito.internal.matchers.Equals;

import com.usecase.dateutil.MeterDataDateUtil;

import lombok.Data;

@Data
public class MeterReadingData implements Comparable<MeterReadingData>{
	
	private Long id;
	private Long reading;
	
	private String month;
	
	private MeterData meterData;
	
	private Long consumption;

	@Override
	public int compareTo(MeterReadingData o) {
		int compareMeterId = this.meterData.getMeterId().compareTo(
				o.meterData.getMeterId());
		if (compareMeterId == 0) {
				return MeterDataDateUtil.convertMonthToDate(this.getMonth()).compareTo(
						MeterDataDateUtil.convertMonthToDate(o.getMonth()));
			}
		else
		return compareMeterId;

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MeterReadingData other = (MeterReadingData) obj;
		if (meterData == null) {
			if (other.meterData != null)
				return false;
		} else if (!meterData.equals(other.meterData))
			return false;
		else if(!this.getMonth().equals(other.getMonth()))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((meterData == null) ? 0 : meterData.hashCode());
		return result;
	}
	
  
	

}
