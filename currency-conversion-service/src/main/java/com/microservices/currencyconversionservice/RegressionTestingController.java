package com.microservices.currencyconversionservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

public class RegressionTestingController {

	@Autowired
	private RequestRepository requestRepository;
	private List<Request> logList;
	
	
	//Run Currency Exchange again on all the old requests
	//@GetMapping@GetMapping("/currency-converter/retest")
	//public List<CurrencyConversionBean> convertCurrency
}
