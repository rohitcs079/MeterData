package com.usecase.model;

import java.math.BigDecimal;

import com.usecase.dateutil.MeterDataDateUtil;

import lombok.Data;

@Data
public class ProfileAndFractionData implements Comparable<ProfileAndFractionData>{
	
	private BigDecimal fraction;
	
	private String month;
	
	private MeterData meterData;

	@Override
	public int compareTo(ProfileAndFractionData o) {
		int compareMeterId = this.meterData.getMeterId().compareTo(
				o.meterData.getMeterId());
		if (compareMeterId == 0) {
			
				return MeterDataDateUtil.convertMonthToDate(this.getMonth()).compareTo(
						MeterDataDateUtil.convertMonthToDate(o.getMonth()));

		}
		return compareMeterId;
	}
	

}
