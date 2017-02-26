package com.usecase.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.usecase.constants.MonthCodeEnum;
import com.usecase.domain.Meter;
import com.usecase.domain.ProfileAndFraction;
import com.usecase.model.MeterData;
import com.usecase.model.MeterReadingData;
import com.usecase.model.ProfileAndFractionData;
import com.usecase.repository.MeterRepository;
import com.usecase.repository.ProfileAndFractionRepository;
import com.usecase.service.ProfileAndFractionService;
import com.usecase.utility.MeterDataTranslator;

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
	

	public List<MeterReadingData> validateCreateMeterReading(List<MeterReadingData> meterReadingDataList) {
        List<MeterReadingData> validMeterReadingDatas=validateMeterIdAndProfile(meterReadingDataList);
        if(validMeterReadingDatas!=null && !validMeterReadingDatas.isEmpty())
        {
        	Map<String, List<MeterReadingData>> map=convertListToMap(validMeterReadingDatas);
        	Map<String, List<MeterReadingData>> validMeterReadingMap=validateMeterReading(map);
        	if(validMeterReadingMap!=null && !validMeterReadingMap.isEmpty())
        	{
        		//validMeterReadingMap=validateFractionExist(validMeterReadingMap);
        		validMeterReadingDatas=null;
        		validMeterReadingDatas=new ArrayList<MeterReadingData>();
        		for(String key:validMeterReadingMap.keySet())
        			validMeterReadingDatas.addAll(validMeterReadingMap.get(key));
        		
        	}
        }
        return validMeterReadingDatas;
	}

	private List<MeterReadingData> validateMeterIdAndProfile(List<MeterReadingData> meterReadingDatas)
	{
		List<MeterReadingData> validMeterReadingDatas=new ArrayList<MeterReadingData>();
		for(MeterReadingData meterReadingData : meterReadingDatas)
		{
			Meter meter = meterRepository.findByMeterId(meterReadingData.getMeterData().getMeterId());
			if(meter!=null)
			{
				validMeterReadingDatas.add(meterReadingData);
			}
		}
		return validMeterReadingDatas;
	}
	
	private Map<String, List<MeterReadingData>> validateFractionExist(Map<String, List<MeterReadingData>> meterReadingMap)
	{
		Map<String, List<MeterReadingData>> validMeterReadingData=new HashMap<String, List<MeterReadingData>>();
		for(String key:meterReadingMap.keySet())
		{
			Meter meter=meterRepository.findByMeterId(meterReadingMap.get(key).get(0).getMeterData().getMeterId());
			List<ProfileAndFraction> frationList=fractionRepository.findByMeterId(meter.getId());
			if(frationList!=null && !frationList.isEmpty())
			{
				List<ProfileAndFractionData> fractionDataList=meterDataTranslator.populateFractionDataToModel(frationList);
				Collections.sort(fractionDataList);
				if(validateConsumptionTolerance(meterReadingMap.get(key), fractionDataList))
				validMeterReadingData.put(key, meterReadingMap.get(key));
				
			}
		}
		return validMeterReadingData;
		
	}
	private boolean validateConsumptionTolerance(List<MeterReadingData> meterReadingDatas,List<ProfileAndFractionData> fractionDataList)
	{
		boolean result=true;
		for(int i=0;i<meterReadingDatas.size();i++)
		{
			MeterReadingData currentReadingData=meterReadingDatas.get(i);
			MeterReadingData overAllReading=meterReadingDatas.get(11);
			ProfileAndFractionData fractionData=fractionDataList.get(i);
			BigDecimal value=BigDecimal.valueOf(overAllReading.getReading()).multiply(fractionData.getFraction());
			BigDecimal max=value.add(value.multiply(BigDecimal.valueOf(0.25)));
			BigDecimal min=value.subtract(value.multiply(BigDecimal.valueOf(0.25)));
			if(BigDecimal.valueOf(currentReadingData.getConsumption()).compareTo(min)>=0 && BigDecimal.valueOf(currentReadingData.getConsumption()).compareTo(max)<=0)
			{}
			else
			{
				result=false;
				break;
			}
				
		}
		return result;
		
	}
	
	private Map<String, List<MeterReadingData>> validateMeterReading(Map<String, List<MeterReadingData>> map) {
		boolean result = true;
		Map<String, List<MeterReadingData>> validMap=new HashMap<String, List<MeterReadingData>>();
		for (String key : map.keySet()) {
			
			long prevReading = 0;
			List<MeterReadingData> data=map.get(key);
			Collections.sort(data);
			for(MeterReadingData meter:data)
			{
				if(meter.getMonth().equals(MonthCodeEnum.JAN.name()))
					prevReading=meter.getReading();
				else if(prevReading>meter.getReading())
				{
					result=false;
					break;
				}
				else
					prevReading=meter.getReading();
			}
			if(result)
			{
				List<MeterReadingData> calculatedConsumptionList=data;
				MeterReadingData meterReadingData=null;
				for(int i=0;i<calculatedConsumptionList.size();i++)
				{
					if(i==0)
					{
						meterReadingData=calculatedConsumptionList.get(0);
						meterReadingData.setConsumption(meterReadingData.getReading());
					}
					else
					{
						meterReadingData=calculatedConsumptionList.get(i);
						meterReadingData.setConsumption(calculatedConsumptionList.get(i).getReading()-calculatedConsumptionList.get(i-1).getReading());
					}
				}
				
				validMap.put(key, calculatedConsumptionList);
			}

		}

		return validMap;
	}

	private Map<String, List<MeterReadingData>> convertListToMap(List<MeterReadingData> meterReadingDatas)
	{
		List<MeterReadingData> meterReadingList = null;
		Map<String, List<MeterReadingData>> map = new HashMap<String, List<MeterReadingData>>();

		MeterReadingData meterReading;
		for (MeterReadingData meterReadingData : meterReadingDatas) {
			meterReading = new MeterReadingData();
			MeterData meterData = new MeterData(meterReadingData.getMeterData().getMeterId(),meterReadingData.getMeterData().getProfile());
			meterReading.setMeterData(meterData);
			meterReading.setMonth(meterReadingData.getMonth());
			meterReading.setReading(meterReadingData.getReading());
			if (!map.containsKey(meterReadingData.getMeterData().getMeterId())) {
				meterReadingList = new ArrayList<MeterReadingData>();

				meterReadingList.add(meterReading);
				map.put(meterReadingData.getMeterData().getMeterId(), meterReadingList);
			} else {
				List<MeterReadingData> getlist = map.get(meterReadingData.getMeterData().getMeterId());
				getlist.add(meterReading);
				map.remove(meterReadingData.getMeterData().getMeterId());
				map.put(meterReadingData.getMeterData().getMeterId(), getlist);
			}

		
	}
		return map;
		
	}
	
	private List<MeterReadingData> convertMapToList(Map<String, List<MeterReadingData>> map)
	{
		List<MeterReadingData> meterReadingList = new ArrayList<MeterReadingData>();
		for(String key:map.keySet())
			meterReadingList.addAll(map.get(key));
		
		return meterReadingList;
		
		
	}
}
