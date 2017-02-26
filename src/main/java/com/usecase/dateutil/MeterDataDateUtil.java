package com.usecase.dateutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lombok.extern.log4j.Log4j;

import com.usecase.constants.MonthCodeEnum;

/**
 * Date util class to perform date operations related to usecase
 * 
 * @author ROHIT
 *
 */

@Log4j
public class MeterDataDateUtil {

	static int[] nonLeapYear = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	static int[] leapYear = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	/**
	 * Takes a calendar Month e.g. JAN
	 * 
	 * @param month
	 * @return Last day of month for given Month
	 */

	public static Date convertMonthToDate(String month) {
		Date dateFromMonth = null;

		int year = Calendar.getInstance().get(Calendar.YEAR); // Gets the
																// current date
																// and time
		String dateInString = null;
		MonthCodeEnum mon = MonthCodeEnum.valueOf(month);
		SimpleDateFormat dateFormattter = new SimpleDateFormat("dd/MMM/yyyy");
		if (month.equals(MonthCodeEnum.FEB)) {
			dateInString = leapYear[mon.getValue()] + "/" + month + "/" + year;
		} else
			dateInString = nonLeapYear[mon.getValue()] + "/" + month + "/"
					+ year;

		try {
			dateFromMonth = dateFormattter.parse(dateInString);
		} catch (ParseException e) {
			log.error("Error in Month to Date Conversion" + e);
		}
		return dateFromMonth;

	}

}
