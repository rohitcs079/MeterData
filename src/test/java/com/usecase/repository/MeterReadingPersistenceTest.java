package com.usecase.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
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



@RunWith(SpringRunner.class)
@SpringBootTest(classes = MeterDataApplicationTests.class)
@DataJpaTest
public class MeterReadingPersistenceTest {
	
	   @Autowired
	   private TestEntityManager entityManager;
		
		@Autowired
		private MeterReadingRepository repository;
		
		@Autowired
		private MeterRepository meterRepository;
		
		@Test
		public void findByMeterIdShouldReturnMeterReading()
		{
			Meter meter = new Meter();
			meter.setMeterId("0012");
			meter.setProfile("ABCD");
			this.entityManager.persist(meter);
			meter = this.meterRepository.findByMeterId("0012");
			
			MeterReading meterReading1 = new MeterReading();
			meterReading1.setMeter(meter);
			meterReading1.setReading(20L);
			meterReading1.setReadingDate(DateUtil.now());
			this.entityManager.persist(meterReading1);

			MeterReading meterReading2 = new MeterReading();
			meterReading2.setMeter(meter);
			meterReading2.setReading(30L);
			meterReading2.setReadingDate(DateUtil.now());
			this.entityManager.persist(meterReading2);
			
			List<MeterReading>meterReadingList = this.repository.findByMeter(meter.getId());
			assertThat(meterReadingList.size()).isEqualTo(2);
		}
		
		

}
