package com.usecase.utility;

import java.util.List;

import lombok.Data;

import com.usecase.model.MeterReadingData;

@Data
public class MeterReadingValidationUtil {
	
	// Contains Meter Reading validations message
	List<CustomErrorMessage> validationError;
	
	// Contains valid Meter Readings
	List<MeterReadingData> validMeterReadings;
	
	

}
