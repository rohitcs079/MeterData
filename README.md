# Oroprise

UseCase for Meter Readings and Consumption

#DataBase

Application uses h2DataBase. To view the database contents use below console
hhtp://localhost:8080/h2-console

## TO Run the application

 run: mvn spring-boot:run
    
    
## REST APIs available and Tested

To create MeterData call

POST http://localhost:8090/api/meterreading/createMeter

To System test creation of fraction,
POST http://localhost:8080/api/fractions/


To System test creation of meterreadings,
POST http://localhost:8090/api/meterreading/createMeterReading

#Assumptions
 For a Profile  12 months data will be loaded otherwise it wont process
 Meter and Profile Data will be inserted before processing Fraction and Meter Readings
 
 #TODO:
   Although Rest end points for all the CRUD operation has been implemented. Below are the TODO List
    1.Test Update, Delete and Read operation for MeterReadings.
	2.Test Update, Delete and Read operation for MeterReadings.
	3.Develop Unit testcases for Service methods and Spring data rest repository.
	4. Validation for MeterReading value in range of the fraction
	
	
