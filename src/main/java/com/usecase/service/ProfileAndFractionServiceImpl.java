package com.usecase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usecase.domain.Meter;
import com.usecase.domain.MeterReading;
import com.usecase.domain.ProfileAndFraction;
import com.usecase.model.ProfileAndFractionData;
import com.usecase.repository.ProfileAndFractionRepository;
import com.usecase.utility.MeterDataTranslator;

@Service
public class ProfileAndFractionServiceImpl implements ProfileAndFractionService {
	
	@Autowired
	private MeterDataTranslator meterDataTranslator;
	
	@Autowired
	private ProfileAndFractionRepository profileAndFractionRepository;


	@Override
	public List<ProfileAndFraction> createProfileAndFraction(
			List<ProfileAndFractionData> profileAndFractionDataList) {
		
		
		List<ProfileAndFraction> profileAndFractionList = meterDataTranslator.populateFractionDataForSave(profileAndFractionDataList);
		
		profileAndFractionList =  (List<ProfileAndFraction>) profileAndFractionRepository.save(profileAndFractionList);
		
		return profileAndFractionList;
	}


	@Override
	public void deleteProfileAndFraction(Long id) {
		
		
		profileAndFractionRepository.delete(id);
	}


	@Override
	public void updateProfileAndFraction(List<ProfileAndFractionData> updateList) {

		List<ProfileAndFraction> profileAndFractionList = meterDataTranslator.populateFractionDataForSave(updateList);
	
		profileAndFractionRepository.save(profileAndFractionList);
	}


	@Override
	public List<ProfileAndFraction> getFractionDetail(Meter meter) {
		List<ProfileAndFraction> profileAndFrationList=profileAndFractionRepository.findByMeter(meter.getId());
		return profileAndFrationList;
	}

}
