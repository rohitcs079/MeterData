package com.usecase.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;


@Entity
@Table(name="PROFILE_FRACTION" )
@Data
public class ProfileAndFraction {
	
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
	
	
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name ="READING_DATE")
    private Date fractionMonth;
    
    @Column(name ="FRACTION")
    private BigDecimal fraction;
    
    @ManyToOne
    @JoinColumn(name ="METER_ID")
    private Meter profile;

}
