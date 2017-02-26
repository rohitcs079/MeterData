package com.usecase.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lombok.experimental.UtilityClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usecase.dateutil.MeterDataDateUtil;
import com.usecase.domain.Meter;
import com.usecase.domain.MeterReading;
import com.usecase.model.MeterData;
import com.usecase.model.MeterReadingData;
import com.usecase.repository.MeterReadingRepository;
import com.usecase.repository.MeterRepository;
import com.usecase.utility.MeterDataTranslator;

/**
 * 
 * Service Class for MeterReading CRUD operation Created by ROHIT on 26-02-2017.
 *
 */
@Service
public class MeterReadingServiceImpl implements MeterReadingService {

	@Autowired
	private MeterReadingRepository meterReadingRepository;

	@Autowired
	private MeterRepository meterRepository;

	@Autowired
	private MeterDataTranslator meterDataTranslator;

	@Override
	public List<Meter> createMeter(List<MeterData> meterDataList) {

		List<Meter> meterData = meterDataTranslator
				.populateMeterForSave(meterDataList);

		meterData = (List<Meter>) meterRepository.save(meterData);

		return meterData;
	}

	@Override
	public List<MeterReading> createMeterReading(
			List<MeterReadingData> meterReadingDataList) {

		List<MeterReading> meterReadingData = meterDataTranslator
				.populateMeterReadingForSave(meterReadingDataList);

		meterReadingData = (List<MeterReading>) meterReadingRepository
				.save(meterReadingData);

		return meterReadingData;

	}

	@Override
	public void deleteMeterReading(MeterReading meterReading) {
		meterReadingRepository.delete(meterReading);

	}

	@Override
	public MeterReading findByMeterReadingId(Long id) {

		return meterReadingRepository.findOne(id);
	}

	@Override
	public void updateMeterReading(MeterReading meterReading) {

		meterReadingRepository.save(meterReading);

	}

	@Override
	public Long getConsumptionDetail(Meter meter, String date) {
		Calendar cal = Calendar.getInstance();

		Date readingDate = MeterDataDateUtil.convertMonthToDate(date);

		// Calculate Last day of previous month for given date;
		cal.setTime(readingDate);
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date previousReadingDate = cal.getTime();

		Long currentmonthReading = meterReadingRepository
				.findByMeterAndReadingDate(meter.getId(), readingDate);
		Long previousMonthReading = meterReadingRepository
				.findByMeterAndReadingDate(meter.getId(), previousReadingDate);
		// Consumption is CurrentMonthReading minus PreviousMonth reading
		return currentmonthReading - previousMonthReading;

	}

	@Override
	public List<MeterReading> getConsumptionDetail(Meter meter) {

		List<MeterReading> meterReadingList = meterReadingRepository
				.findByMeter(meter.getId());

		return meterReadingList;
	}

}
