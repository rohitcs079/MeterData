package com.usecase.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * Entity class for METER Table Created by ROHIT on 26-02-2017.
 */

@Entity
@Table(name = "METER")
@Data
public class Meter {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "METER_ID")
	private String meterId;

	@Column(name = "PROFILE")
	private String profile;

	@OneToMany(mappedBy = "meter", cascade = CascadeType.ALL)
	private Set<MeterReading> meterReadings;

	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
	private Set<ProfileAndFraction> profileAndFractions;

}
