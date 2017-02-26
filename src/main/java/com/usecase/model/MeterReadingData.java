package com.usecase.model;

import com.usecase.dateutil.MeterDataDateUtil;

import lombok.Data;

@Data
public class MeterReadingData implements Comparable<MeterReadingData>{
	
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
	

}
