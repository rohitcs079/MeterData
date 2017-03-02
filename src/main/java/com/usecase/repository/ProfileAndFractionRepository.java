package com.usecase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.usecase.domain.ProfileAndFraction;


/**
 *  Repository class to perform DataBase operation on ProfileAndFraction
 * Created by ROHIT on 26-02-2017.
 *
 */
public interface ProfileAndFractionRepository extends JpaRepository<ProfileAndFraction,Long> {
	
	/**
	 * Returns ProfileAndFraction info for a given profile
	 * @param id
	 * @return List<ProfileAndFraction>
	 */
	 @Query("SELECT p FROM ProfileAndFraction p WHERE p.profile.id = :id")
	public List<ProfileAndFraction> findByMeter(@Param(value = "id") Long id);
	 
	 @Query("SELECT p FROM ProfileAndFraction p WHERE p.profile.profile = :profile")
		public List<ProfileAndFraction> findByMeterProfile(@Param(value = "profile") String profile);


}
