package com.usecase.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.usecase.domain.Meter;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DataJpaTest
public class MeterPersistenceTest  {
	
   @Autowired

   private TestEntityManager entityManager;
	
	@Autowired
	private MeterRepository meterRepository;
	
	@Test
	public void findByMeterIdShouldReturnMeter()
	{
		Meter meter = new Meter();
		meter.setMeterId("001");
		meter.setProfile("ABC");
		this.entityManager.persist(meter);
		
		meter = this.meterRepository.findByMeterId("001");
		assertThat(meter.getMeterId()).isEqualTo("001");
	}
	
	

}
