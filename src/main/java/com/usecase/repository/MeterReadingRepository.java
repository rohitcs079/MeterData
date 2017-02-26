package com.usecase.repository;

import java.util.Date;
import java.util.List;

import com.usecase.domain.MeterReading;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by ROHIT on 26-02-2017.
 */

public interface MeterReadingRepository extends CrudRepository<MeterReading,Long> {
	
	/**
	 * Get MeterReading for a meter
	 * @param id
	 * @param readingDate
	 * @return Meter Reading for a meter and month
	 */
	
	Long findByMeterAndReadingDate(@Param("id")  Long id,@Param("readingDate")  Date readingDate);
	
	/**
	 * Get meter reading for a given meter
	 * @param id
	 * @return List<MeterReading>
	 */
	@Query("SELECT p FROM MeterReading p WHERE p.meter.id = :id")
	List<MeterReading> findByMeter(@Param("id") Long id);

}
