package com.usecase.service;

import java.util.List;

import com.usecase.domain.Meter;
import com.usecase.domain.MeterReading;
import com.usecase.model.MeterData;
import com.usecase.model.MeterReadingData;

public interface MeterReadingService {
	
	/**
	 * Create Meter from RequestData
	 * @param meterDataList
	 * @return List<Meter>
	 */
	public List<Meter> createMeter(List<MeterData> meterDataList);
	
	/**
	 *  Create Meter Readings from Request Data
	 * @param meterDataList
	 * @return List<MeterReading> 
	 */
	public List<MeterReading> createMeterReading(List<MeterReadingData> meterDataList);
	
	/**
	 * Deletes given meterReading
	 * @param meterReading
	 */
	public void deleteMeterReading(Long id);
	
	/**
	 * Get MeterReading from id
	 * @param id
	 * @return MeterReading
	 */
	
	public MeterReading findByMeterReadingId(Long id);
	
	/**
	 * Updates a given MeterReading
	 * @param meterReading
	 */
	
	public void updateMeterReading (MeterReading meterReading);
	 /**
	  * Get consumption for given meter and date
	  * @param meter
	  * @param date
	  * @return Consumption Detail
	  */
	public Long getConsumptionDetail(Meter meter, String date);
	
	/**
	 * Get full reading for a given meter
	 * @param meter
	 * @return
	 */
	public List<MeterReading> getConsumptionDetail(Meter meter);

}
