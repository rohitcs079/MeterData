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
import com.usecase.domain.ProfileAndFraction;
import com.usecase.model.MeterData;
import com.usecase.model.MeterReadingData;
import com.usecase.model.ProfileAndFractionData;
import com.usecase.service.MeterReadingService;
import com.usecase.service.ProfileAndFractionService;
import com.usecase.utility.CustomErrorMessage;
import com.usecase.validator.FractionDataValidator;



/**
 * Created by ROHIT on 25-02-2017.
 */



@RestController
@RequestMapping("/fractions")
@Log4j
public class ProfileAndFractionController {
	
	@Autowired
	private ProfileAndFractionService profileAndFractionService;
	
	@Autowired
	private FractionDataValidator fractionvalidator;
	
	
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public ResponseEntity<?> createMeterReading( @RequestBody List<ProfileAndFractionData> dataList)
	{		dataList=fractionvalidator.validate(dataList);
		if(dataList.isEmpty() == true)
		{
			return new ResponseEntity<CustomErrorMessage>(new CustomErrorMessage("All 12 months Data not available for  profile"),HttpStatus.NOT_ACCEPTABLE);
		}
		
		List<ProfileAndFraction> profileAndFractionList = profileAndFractionService.createProfileAndFraction(dataList);
		if(profileAndFractionList.isEmpty() == true)
		{
			return new ResponseEntity<CustomErrorMessage>(new CustomErrorMessage("Unable to insert Profile Fraction Details"),HttpStatus.NOT_ACCEPTABLE);
			
		}
		return new ResponseEntity<String>("SuccessFully Inserted",HttpStatus.ACCEPTED);
	
	}
	
	/**
	 * Method Deletes a MeterReading
	 * @param meterReadingDataList
	 * @return
	 */
	
	@RequestMapping(value="/deleteProfileAndFraction", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteProfileAndFraction(@RequestBody List<ProfileAndFraction> profileAndFractionList)
	{
		
		profileAndFractionService.deleteProfileAndFraction(profileAndFractionList);
	
		return new ResponseEntity<List<ProfileAndFraction>>(HttpStatus.NO_CONTENT);
	
	}
	 // ------------------- Update All profileAndFraction ------------------------------------------------
	 
    @RequestMapping(value = "/updateMeterReading", method = RequestMethod.POST)
    public ResponseEntity<?> updateMeterReading(@RequestBody List<ProfileAndFractionData> profileAndFractionList) {
       /* log.info("Updating User with id {}" +id);
 
        MeterReading currentMeterReading = profileAndFractionService.findByMeterReadingId(id);
 
        if (currentMeterReading == null) {
        	log.error("Unable to update. MeterReading  with id {} not found."+ id);
            return new ResponseEntity<CustomErrorMessage>(new CustomErrorMessage("Unable to upate. MeterReading with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
 
        currentMeterReading.setReading(meterReading.getReading());
        currentMeterReading.setReadingDate(meterReading.getReadingDate());
        currentMeterReading.setMeter(meterReading.getMeter());*/
 
    	profileAndFractionList=fractionvalidator.validate(profileAndFractionList);
    	profileAndFractionService.updateProfileAndFraction(profileAndFractionList);
        return new ResponseEntity<List<ProfileAndFractionData>>(profileAndFractionList, HttpStatus.OK);
    
    }
    	
// -------------------Retrieve Consumption for  Meter------------------------------------------
    
    @RequestMapping(value = "/getFractionForMeter", method = RequestMethod.POST)
    public ResponseEntity<?> getFractionForMeter(@RequestBody Meter meter) {
    	log.info("Fetching Consumption  for Meter and Date");
        List<ProfileAndFraction> fractionList = profileAndFractionService.getFractionDetail(meter);
        if (fractionList == null) {
        	log.error("Consumption detail not found for Given meter and date" +meter.getMeterId());
            return new ResponseEntity<>(new CustomErrorMessage("Consumption for meter " + meter.getMeterId()
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<ProfileAndFraction>>(fractionList, HttpStatus.OK);
    }
}
