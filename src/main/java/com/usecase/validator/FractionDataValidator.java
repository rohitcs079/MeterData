package com.usecase.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usecase.model.ProfileAndFractionData;
import com.usecase.repository.ProfileAndFractionRepository;

@Service
public class FractionDataValidator {
	@Autowired
	private ProfileAndFractionRepository fractionRepository;
	
	/**
	 * Method validates Fraction Data 
	 * @param fractionList
	 * @return List<ProfileAndFractionData> if all 12 months fraction available
	 */
	public List<ProfileAndFractionData> validate(List<ProfileAndFractionData> fractionList)
	{
		List<ProfileAndFractionData> validFractionList=new ArrayList<ProfileAndFractionData>();
		Map<String, List<ProfileAndFractionData>> tempMap=convertListToMap(fractionList);
		tempMap=validateFraction(tempMap);
		validFractionList=convertMapToList(tempMap);
		return validFractionList;
	}
	
	/**
	 * 
	 * @param fractionList
	 * @return
	 */
	private Map<String, List<ProfileAndFractionData>> convertListToMap(List<ProfileAndFractionData> fractionList)
	{
		Map<String, List<ProfileAndFractionData>> tempMap=new HashMap<String, List<ProfileAndFractionData>>();
		ProfileAndFractionData fractionData=null;
		for(ProfileAndFractionData data:fractionList)
		{
			List<ProfileAndFractionData> tempList=null;
			fractionData=new ProfileAndFractionData();
			fractionData.setFraction(data.getFraction());
			fractionData.setMeterData(data.getMeterData());
			fractionData.setMonth(data.getMonth());
			if(!tempMap.containsKey(fractionData.getMeterData().getMeterId()))
			{
				tempList=new ArrayList<ProfileAndFractionData>();
				tempList.add(fractionData);
				tempMap.put(fractionData.getMeterData().getMeterId(),tempList);
			}
			else
			{
				tempList=tempMap.get(fractionData.getMeterData().getMeterId());
				tempList.add(fractionData);
				tempMap.remove(fractionData.getMeterData().getMeterId());
				tempMap.put(fractionData.getMeterData().getMeterId(), tempList);
				
			}
		}
		
		return tempMap;
	}
	
	private Map<String, List<ProfileAndFractionData>> validateFraction(Map<String, List<ProfileAndFractionData>> map)
	{
		Map<String, List<ProfileAndFractionData>> finalList=new HashMap<String, List<ProfileAndFractionData>>();
		for(String key:map.keySet())
		{
			if(map.get(key).size()==12 && BigDecimal.ONE.compareTo(calculateSum(map.get(key)))==0)
				finalList.put(key, map.get(key));
		}
		return finalList;
	}
	
	private BigDecimal calculateSum(List<ProfileAndFractionData> fractionDatas)
	{
		BigDecimal sum=BigDecimal.ZERO;
		for(ProfileAndFractionData data:fractionDatas)
			sum=sum.add(data.getFraction());
		return sum;
	}
	
	private List<ProfileAndFractionData> convertMapToList(Map<String, List<ProfileAndFractionData>> map)
	{
		List<ProfileAndFractionData> finalList=new ArrayList<ProfileAndFractionData>();
		for(String key:map.keySet())
			finalList.addAll(map.get(key));
		return finalList;
	}
}

