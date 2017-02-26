package com.usecase.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Entity Class for MeterReading Created by ROHIT on 24-02-2017.
 */

@Entity
@Table(name = "METER_READING")
@Data
public class MeterReading {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "METER_READING_ID")
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "READING_DATE")
	private Date readingDate;

	@Column(name = "READING")
	private Long reading;

	@ManyToOne
	@JoinColumn(name = "METER_ID")
	private Meter meter;

}
