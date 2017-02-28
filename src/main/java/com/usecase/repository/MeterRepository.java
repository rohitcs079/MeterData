package com.usecase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.usecase.domain.Meter;


/**
 * Created by ROHIT on 25-02-2017.
 */
public interface MeterRepository extends JpaRepository<Meter,Long>
{
	
	/**
	 *  Get meter based on Meter ID
	 * @param meterId
	 * @return Meter
	 */
	 
	public Meter findByMeterId(@Param(value = "meterId") String meterId);

}
