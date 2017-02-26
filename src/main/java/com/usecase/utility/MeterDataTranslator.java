package com.usecase.utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.usecase.dateutil.MeterDataDateUtil;
import com.usecase.domain.Meter;
import com.usecase.domain.MeterReading;
import com.usecase.domain.ProfileAndFraction;
import com.usecase.model.MeterData;
import com.usecase.model.MeterReadingData;
import com.usecase.model.ProfileAndFractionData;
import com.usecase.repository.MeterRepository;

/**
 * Translator class to convert Request Data to Domain data
 * 
 * @author ROHIT
 *
 */

@Component
public class MeterDataTranslator {

	@Autowired
	private MeterRepository meterRepository;

	/**
	 * Transforms MeterData to Domain Object for Save
	 * 
	 * @param meterDataList
	 * @return List<Meter>
	 */
	public List<Meter> populateMeterForSave(List<MeterData> meterDataList) {
		List<Meter> meterList = new ArrayList<Meter>();
		for (MeterData meterData : meterDataList) {
			Meter meter = new Meter();
			meter.setMeterId(meterData.getMeterId());
			meter.setProfile(meterData.getProfile());
			meterList.add(meter);
		}
		return meterList;

	}

	/**
	 * Transforms MeterReadingData to Domain Object for Save
	 * 
	 * @param meterDataList
	 * @return List<Meter>
	 */
	public List<MeterReading> populateMeterReadingForSave(
			List<MeterReadingData> meterReadingDataList) {
		List<MeterReading> meterReadingList = new ArrayList<MeterReading>();
		for (MeterReadingData meterReadingData : meterReadingDataList) {
			Meter meter = meterRepository.findByMeterId(meterReadingData
					.getMeterData().getMeterId());
			String calendarMonth = meterReadingData.getMonth();
			MeterReading meterReading = new MeterReading();
			meterReading.setMeter(meter);
			meterReading.setReadingDate(MeterDataDateUtil
					.convertMonthToDate(calendarMonth));
			meterReading.setReading(meterReadingData.getReading());
			meterReadingList.add(meterReading);

		}
		return meterReadingList;

	}

	public List<ProfileAndFraction> populateFractionDataForSave(
			List<ProfileAndFractionData> profileAndFractionDataList) {
		List<ProfileAndFraction> profileAndFractionList = new ArrayList<ProfileAndFraction>();
		for (ProfileAndFractionData profileAndFractionData : profileAndFractionDataList) {
			Meter meter = meterRepository.findByMeterId(profileAndFractionData
					.getMeterData().getMeterId());
			String calendarMonth = profileAndFractionData.getMonth();
			ProfileAndFraction profileAndFraction = new ProfileAndFraction();
			profileAndFraction
					.setFraction(profileAndFractionData.getFraction());
			profileAndFraction.setProfile(meter);
			profileAndFraction.setFractionMonth(MeterDataDateUtil
					.convertMonthToDate(calendarMonth));
			profileAndFractionList.add(profileAndFraction);

		}

		return profileAndFractionList;

	}

	public List<ProfileAndFractionData> populateFractionDataToModel(
			List<ProfileAndFraction> profileAndFractionList) {
		List<ProfileAndFractionData> profileAndFractionDataList = new ArrayList<ProfileAndFractionData>();
		for (ProfileAndFraction profileAndFraction : profileAndFractionList) {
			Meter meter = profileAndFraction.getProfile();
			Date date = profileAndFraction.getFractionMonth();
			String calendarMonth = new SimpleDateFormat("MMM").format(date);
			ProfileAndFractionData profileAndFractionData = new ProfileAndFractionData();
			profileAndFractionData
					.setFraction(profileAndFraction.getFraction());
			profileAndFractionData.setMeterData(new MeterData(meter
					.getMeterId(), meter.getProfile()));
			profileAndFractionData.setMonth(calendarMonth.toUpperCase());
			profileAndFractionDataList.add(profileAndFractionData);

		}

		return profileAndFractionDataList;

	}

}
