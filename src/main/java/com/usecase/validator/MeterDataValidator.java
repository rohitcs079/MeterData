package com.usecase.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.usecase.constants.MonthCodeEnum;
import com.usecase.dateutil.MeterDataDateUtil;
import com.usecase.domain.Meter;
import com.usecase.domain.MeterReading;
import com.usecase.domain.ProfileAndFraction;
import com.usecase.model.MeterData;
import com.usecase.model.MeterReadingData;
import com.usecase.model.ProfileAndFractionData;
import com.usecase.repository.MeterRepository;
import com.usecase.repository.ProfileAndFractionRepository;
import com.usecase.service.ProfileAndFractionService;
import com.usecase.utility.CustomErrorMessage;
import com.usecase.utility.MeterDataTranslator;
import com.usecase.utility.MeterReadingValidationUtil;

/**
 * Class to validate MeterReading
 * @author ROHIT
 *
 */
@Service
public class MeterDataValidator  {
	@Autowired
	private MeterRepository meterRepository;
	
	@Autowired
	private MeterDataTranslator meterDataTranslator;
	
	@Autowired
	private ProfileAndFractionRepository fractionRepository;
	

	/**Method validates the meter reading entries
	 * 
	 * @param meterReadingDataList
	 * @return
	 */
	public MeterReadingValidationUtil validateCreateMeterReading(List<MeterReadingData> meterReadingDataList) {
		
		Map<MeterData,List<MeterReadingData>> meterReadingByMeter = meterReadingDataList.stream().collect(Collectors.groupingBy(MeterReadingData::getMeterData));
		
		List<CustomErrorMessage> customErrorMessages = new ArrayList<CustomErrorMessage>();
		List<MeterReadingData> validMeterReadings  = new ArrayList<MeterReadingData>();
		MeterReadingValidationUtil meterReadingValidationUtil = new MeterReadingValidationUtil();
		
		for(Map.Entry<MeterData, List<MeterReadingData>> entry:meterReadingByMeter.entrySet() )
		{
			List<MeterReadingData> currentMeterReadingData = entry.getValue();
			MeterData currentMeterData = entry.getKey();
			//For checking Duplicate month data
			Set<MeterReadingData> meterReadingSet = new HashSet<>(currentMeterReadingData);
			
			if(meterReadingSet.size() != 12)
			{
				customErrorMessages.add(new CustomErrorMessage("Please pass  readings for 12 months for meter " + currentMeterData.getMeterId()));
				continue;
			}
			//Sort the meter reading on calendar month 
			Collections.sort(currentMeterReadingData);
			
			boolean readingDataForMonth = validReadingDataForEachMonth(currentMeterReadingData);
			
			if(readingDataForMonth == false)
			{
				customErrorMessages.add(new CustomErrorMessage("Current month reading less than previous one ." + currentMeterData.getMeterId()));
				continue;
			}
			
			//Check if profile and fraction exists
			List<ProfileAndFraction> profileAndFarctions = fractionRepository.findByMeterProfile(currentMeterData.getProfile());
			if(profileAndFarctions.isEmpty() == true)
			{
				customErrorMessages.add(new CustomErrorMessage("Profile And Reaction Data not available ." + currentMeterData.getMeterId()));
				continue;
			}
			// Check if reading is consistent with fraction
			 boolean isReadingInRange = validateTolerance(currentMeterReadingData, profileAndFarctions, customErrorMessages);
			 if(isReadingInRange == false)
			 {
					customErrorMessages.add(new CustomErrorMessage("Reading is not consistent with fraction" + currentMeterData.getMeterId()));
					continue;
			 }
			
			 validMeterReadings.addAll(currentMeterReadingData);
			
		}
		meterReadingValidationUtil.setValidMeterReadings(validMeterReadings);
		meterReadingValidationUtil.setValidationError(customErrorMessages);
        return meterReadingValidationUtil;
	}
	
	private boolean validReadingDataForEachMonth(List<MeterReadingData> readings)
	{
		MeterReadingData current;
		MeterReadingData previous;
		
		for (int i =0 ; i<readings.size(); i++)
		{
			if(i==0)
			continue;
			current= readings.get(i);
			previous = readings.get(i-1);
			if(previous != null &&(current.getReading().compareTo(previous.getReading())<0))
				return false;
			
		}
		return true;
	}
	
	private boolean validateTolerance(List<MeterReadingData> readings, List<ProfileAndFraction> profiles, List<CustomErrorMessage> validationMessages)
	{
		Long totalReadingsForYear = readings.stream().mapToLong(MeterReadingData::getReading).sum();
		Long currentReading;
	
		ProfileAndFraction fraction;
		for(MeterReadingData reading: readings)
		{
			 Date currentMonth = MeterDataDateUtil.convertMonthToDate(reading.getMonth());
			currentReading = reading.getReading();
			fraction = profiles.stream().filter(a->currentMonth.compareTo(a.getFractionMonth())==0).findAny().orElse(null);
			if(fraction != null)
			{
				return  validToleranceRange(currentReading, fraction.getFraction(), totalReadingsForYear);
			}
			
		}
		return false;
		
	}
	
	/**
	 * Validates reading in range of fraction
	 * @param reading
	 * @param fraction
	 * @param totalReadings
	 * @return 
	 */
			
	private boolean validToleranceRange(Long reading, BigDecimal fraction, Long totalReadings )
	{
	
		Double  midRange = totalReadings * (fraction.doubleValue());
		Double diff  = 0.25*midRange;
		Double min = midRange - diff;
		Double max = midRange + diff;
		
		if(reading.doubleValue()>min && reading.doubleValue()<max )
			return true;
		
		return false;
	}
}
