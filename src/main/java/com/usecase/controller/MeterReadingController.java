package com.usecase.controller;

import java.util.List;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.usecase.domain.Meter;
import com.usecase.domain.MeterReading;
import com.usecase.model.MeterData;
import com.usecase.model.MeterReadingData;
import com.usecase.service.MeterReadingService;
import com.usecase.utility.CustomErrorMessage;
import com.usecase.validator.MeterDataValidator;

/**
 * 
 * Rest End point controller for MeterReading Process. Also have rest end point
 * to populate meter and profile info Created by ROHIT on 25-02-2017.
 */

@RestController
@RequestMapping("/api/meterreading")
@Log4j
public class MeterReadingController {

	@Autowired
	private MeterReadingService meterReadingService;

	@Autowired
	MeterDataValidator meterReadingValidator;

	/**
	 * Method creates Meter Data with meter and profile
	 */
	@RequestMapping(value = "/createMeter", method = RequestMethod.POST)
	public ResponseEntity<?> createMeterData(
			@RequestBody List<MeterData> meterDataList) {

		List<Meter> meterList = meterReadingService.createMeter(meterDataList);
		if (meterList.isEmpty() == true) {
			return new ResponseEntity<CustomErrorMessage>(
					new CustomErrorMessage("Unable to insert Meter"),
					HttpStatus.CONFLICT);

		}
		return new ResponseEntity<String>("SuccessFully Inserted",
				HttpStatus.ACCEPTED);

	}

	/**
	 * Creates Meter Reading for meter for different profiles
	 * 
	 * @param meterReadingDataList
	 * @return
	 */
	@RequestMapping(value = "/createMeterReading", method = RequestMethod.POST)
	public ResponseEntity<?> createMeterReading(
			@RequestBody List<MeterReadingData> meterReadingDataList) {
		meterReadingDataList = meterReadingValidator
				.validateCreateMeterReading(meterReadingDataList);
		List<MeterReading> meterReadingList = meterReadingService
				.createMeterReading(meterReadingDataList);
		if (meterReadingList.isEmpty() == true) {
			return new ResponseEntity<CustomErrorMessage>(
					new CustomErrorMessage("Unable to insert MeterReading"),
					HttpStatus.NOT_ACCEPTABLE);

		}
		return new ResponseEntity<String>("SuccessFully Inserted",
				HttpStatus.ACCEPTED);

	}

	/**
	 * Method Deletes a MeterReading
	 * 
	 * @param meterReadingDataList
	 * @return
	 */

	@RequestMapping(value = "/deleteMeterReading", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteMeterReading(
			@RequestBody MeterReading meterReading) {

		meterReadingService.deleteMeterReading(meterReading);

		return new ResponseEntity<MeterReading>(HttpStatus.NO_CONTENT);

	}

	// ------------------- Update a MeterReading
	// ------------------------------------------------

	@RequestMapping(value = "/updateMeterReading/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateMeterReading(@PathVariable("id") long id,
			@RequestBody MeterReading meterReading) {
		log.info("Updating User with id {}" + id);

		MeterReading currentMeterReading = meterReadingService
				.findByMeterReadingId(id);

		if (currentMeterReading == null) {
			log.error("Unable to update. MeterReading  with id {} not found."
					+ id);
			return new ResponseEntity<CustomErrorMessage>(
					new CustomErrorMessage(
							"Unable to upate. MeterReading with id " + id
									+ " not found."), HttpStatus.NOT_FOUND);
		}

		currentMeterReading.setReading(meterReading.getReading());
		currentMeterReading.setReadingDate(meterReading.getReadingDate());
		currentMeterReading.setMeter(meterReading.getMeter());

		meterReadingService.updateMeterReading(currentMeterReading);
		return new ResponseEntity<MeterReading>(currentMeterReading,
				HttpStatus.OK);

	}

	// -------------------Retrieve Consumtion for given Date and
	// Meter------------------------------------------

	@RequestMapping(value = "/getConsumtionForDateAndMeter", method = RequestMethod.POST)
	public ResponseEntity<?> getConsumptionForDateAndMeter(
			@RequestBody Meter meter, @RequestBody String calendarMonth) {
		log.info("Fetching Consumption  for Meter and Date");
		Long consumption = meterReadingService.getConsumptionDetail(meter,
				calendarMonth);
		if (consumption == null) {
			log.error("Consumption detail not found for Given meter and date"
					+ meter.getMeterId() + calendarMonth);
			return new ResponseEntity<>(new CustomErrorMessage(
					"Consumption for meter " + meter.getMeterId()
							+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Long>(consumption, HttpStatus.OK);
	}

	// -------------------Retrieve Consumption for
	// Meter------------------------------------------

	@RequestMapping(value = "/getConsumtionForMeter", method = RequestMethod.POST)
	public List<MeterReading> getConsumptionForMeter(@RequestBody Meter meter) {
		log.info("Fetching Consumption  for Meter and Date");
		List<MeterReading> meterReadingList = meterReadingService
				.getConsumptionDetail(meter);
		if (meterReadingList == null) {
			log.error("Consumption detail not found for Given meter and date"
					+ meter.getMeterId());
			/*
			 * return new ResponseEntity<MeterReading>(new
			 * CustomErrorMessage("Consumption for meter " + meter.getMeterId()
			 * + " not found"), HttpStatus.NOT_FOUND);
			 */
		}
		return meterReadingList;
	}

}
