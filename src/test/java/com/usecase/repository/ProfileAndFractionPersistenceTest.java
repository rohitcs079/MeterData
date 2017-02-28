package com.usecase.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.assertj.core.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.usecase.MeterDataApplicationTests;
import com.usecase.domain.Meter;
import com.usecase.domain.MeterReading;
import com.usecase.domain.ProfileAndFraction;



@RunWith(SpringRunner.class)
@SpringBootTest(classes=MeterDataApplicationTests.class)
@DataJpaTest
public class ProfileAndFractionPersistenceTest {
	
	   @Autowired
	   private TestEntityManager entityManager;
		
		@Autowired
		private ProfileAndFractionRepository repository;
		
		@Autowired
		private MeterRepository meterRepository;
		
		
		@Test
		public void findByMeterIdShouldReturnFractionAndProfile()
		{
			Meter meter = new Meter();
			meter.setMeterId("0012");
			meter.setProfile("ABCD");
			this.entityManager.persist(meter);
			meter = this.meterRepository.findByMeterId("0012");
			
			ProfileAndFraction profileAndFraction = new ProfileAndFraction();
			profileAndFraction.setFraction(new BigDecimal(0.2));
			profileAndFraction.setFractionMonth(DateUtil.now());
			profileAndFraction.setProfile(meter);
			
			this.entityManager.persist(profileAndFraction);

			ProfileAndFraction profileAndFraction1 = new ProfileAndFraction();
		
			profileAndFraction1.setFraction(new BigDecimal(0.3));
			profileAndFraction1.setFractionMonth(DateUtil.now());
			profileAndFraction1.setProfile(meter);
			
			this.entityManager.persist(profileAndFraction1);
			
			List<ProfileAndFraction>profileAndFractions = this.repository.findByMeter(meter.getId());
			assertThat(profileAndFractions.size()).isEqualTo(2);
		}
		

}
