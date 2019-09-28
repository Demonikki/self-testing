package com.microservices.currencyconversionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages={"com.microservices.currencyconversionservice"})
public class CurrencyConversionServiceApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(CurrencyConversionServiceApplication.class, args);
		CurrencyConversionController controller = 	(CurrencyConversionController)applicationContext
													.getBean(CurrencyConversionController.class);
		
		//update currentVersion and run tests on reload
		controller.currentVersion = 1;
		controller.regressionTest();
		
	}

}
