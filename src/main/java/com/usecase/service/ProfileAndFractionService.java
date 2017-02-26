package com.usecase.service;

import java.util.List;

import com.usecase.domain.Meter;
import com.usecase.domain.ProfileAndFraction;
import com.usecase.model.ProfileAndFractionData;

public interface ProfileAndFractionService {
	
	/**
	 * Creates Fraction from given request
	 * @param profileAndFractionDataList
	 * @return List<ProfileAndFraction>
	 */
	public List<ProfileAndFraction> createProfileAndFraction(List<ProfileAndFractionData> profileAndFractionDataList);
	
	/**
	 * Delete Fraction 
	 * @param deleteList
	 */
	
	public void deleteProfileAndFraction(List<ProfileAndFraction> deleteList);
	
	/**
	 * Update Fraction Data
	 * @param profileAndFractionList
	 */
	
	public void updateProfileAndFraction(List<ProfileAndFractionData> profileAndFractionList);

	/**
	 * Get FractionData  for a given Meter
	 * @param meter
	 * @return
	 */
	public List<ProfileAndFraction> getFractionDetail(Meter meter);
	
	
}
