package com.usecase.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.usecase.domain.Meter;


/**
 * Created by ROHIT on 25-02-2017.
 */
public interface MeterRepository extends CrudRepository<Meter,Long>
{
	
	/**
	 *  Get meter based on ID
	 * @param meterId
	 * @return Meter
	 */
	 @Query("SELECT p FROM Meter p WHERE p.meterId = :meterId")
	public Meter findByMeterId(@Param(value = "meterId") String meterId);

}
