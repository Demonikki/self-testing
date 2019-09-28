package com.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.io.FileWriter;
import java.io.IOException;


@RestController
public class CurrencyConversionController {
		
	
		static private RequestLog requests = new RequestLog();
		static private List<Request> logList;
		public int currentVersion = 0;
		
		@Autowired
		private RequestRepository requestRepository;
		
		
				
		
		@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
		public CurrencyConversionBean convertCurrency(@PathVariable String from,
													  @PathVariable String to, @PathVariable BigDecimal quantity) {
			
			Map<String,String> uriVariables = new HashMap<>(); 
			uriVariables.put("from", from);
			uriVariables.put("to", to);
			
			ResponseEntity<CurrencyConversionBean>responseEntity = 
					new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
					CurrencyConversionBean.class, uriVariables);
			
			CurrencyConversionBean response = responseEntity.getBody();

			
			BigDecimal convMul = response.getConversionMultiple();
					//	**	v1 code	**	//
			//BigDecimal convMul = response.getConversionMultiple().add(new BigDecimal(2));
			
			
					
			BigDecimal total = quantity.multiply(convMul);
			
			
			System.out.println("\nCURRENT VERSION: v"+currentVersion);
			
			//Store into requestLog
			requests.addRequest(new Request(from,to,quantity,convMul,total));
					
			
			return new CurrencyConversionBean(response.getId(), from, to, convMul, quantity, total, response.getPort());
		}
			
		
		@GetMapping("/currency-converter/regtest")
		public void regressionTest() {
			
			System.out.println("Regression testing. Current version: v"+currentVersion);
			if(currentVersion==0){
				System.out.println("No tests for v0");
				return;
			}
			//load in JSON logs of previous version
			
			boolean error = false;
			JSONParser parser = new JSONParser();

			JSONObject jsonObject = null;
	        try (Reader reader = new FileReader("log_v0.json")) {

	            jsonObject = (JSONObject) parser.parse(reader);
	            //System.out.println(jsonObject);

	        } catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
	        
	        String old_from = "", old_to = "";
			BigDecimal old_quantity = null, old_total = null, old_convMul = null;
			RequestLog old_requests = new RequestLog();
			RequestLog new_requests = new RequestLog();
			List<Request> diffs = new ArrayList<>();
	        
	        JSONArray logs = (JSONArray)jsonObject.get("requests");
	        Iterator<JSONObject> iterator = logs.iterator();
            while (iterator.hasNext()) {
            	JSONObject log = iterator.next();
            	//System.out.println(log);
            	old_from = (String) log.get("from");	
            	old_to = (String) log.get("to");
            	old_quantity = new BigDecimal (log.get("quantity").toString());
            	old_total = new BigDecimal (log.get("total").toString());	
            	old_convMul = new BigDecimal (log.get("conversion multiple").toString());
            	Request old_request = new Request(old_from,old_to,old_quantity,old_total,old_convMul);
               
            
            	//Compare 'old_requests' with live responses
	        
            	/*
            	 
	            Map<String,String> uriVariables = new HashMap<>(); 
				uriVariables.put("from", old_from);
				uriVariables.put("to", old_to);
							
				ResponseEntity<CurrencyConversionBean>responseEntity = 
						new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
						CurrencyConversionBean.class, uriVariables);
				*/
				
				CurrencyConversionBean response = convertCurrency(old_from, old_to, old_quantity);	//old inputs, current build
				
				BigDecimal new_total = old_quantity.multiply(response.getConversionMultiple());
								
				//System.out.println(old_total+" -> "+new_total);
				if(!(new_total.compareTo(old_total)==0)) {							//avoids decimal pointer issues
					error = true;
					//Difference in output found - possibly more test cases
					System.out.println("Converting "+old_quantity+" "+old_from
							+" to "+old_to+" gave "+old_total+" in v"+(currentVersion-1)
							+" but now gives "+new_total+" in v"+(currentVersion));
				}
            }
            if(!error)
            	System.out.println("No changes detected in v"+currentVersion);
		}
		
		
		
		@GetMapping("/currency-converter/retest")
		public List<Request> retest () {
			String from, to;
			BigDecimal quantity;
			RequestLog rerequests = new RequestLog();
			List<Request> diffs = new ArrayList<>();
			
			for (Request r: logList) {
				from = r.getFrom();
				to = r.getTo();
				quantity = r.getQuantity();
				
				Map<String,String> uriVariables = new HashMap<>(); 
				uriVariables.put("from", from);
				uriVariables.put("to", to);
				
				ResponseEntity<CurrencyConversionBean>responseEntity = 
						new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
						CurrencyConversionBean.class, uriVariables);
				
				CurrencyConversionBean response = responseEntity.getBody();
				
				BigDecimal total = quantity.multiply(response.getConversionMultiple());
				
				rerequests.addRequest(new Request(from,to,quantity,response.getConversionMultiple(),total));
			}
			
			//Check that rerequests is the same as requests
			for (int i=0; i<requests.getRequests().size(); i++) {
				Request req, rereq;
				req = requests.getRequests().get(i);
				rereq = rerequests.getRequests().get(i);
				if(!req.equals(rereq)) diffs.add(rereq);
				
			}
			
			return diffs;
			//return rerequests.getRequests();
		}
		


		@PostMapping("/currency-converter/log-requests")
		public boolean logRequests() {
			//need to write to file or database, store persistently
			JSONObject log = new JSONObject();
			JSONArray reqs = new JSONArray();
			for(Request r: requests.getRequests()) {
				requestRepository.save(r);
				//write to file
				JSONObject jreq = new JSONObject();
				jreq.put("from", r.getFrom());
				jreq.put("to", r.getTo());
				jreq.put("conversion multiple", r.getConversionMultiple());
				jreq.put("quantity", r.getQuantity());
				jreq.put("total", r.getTotal());
				reqs.add(jreq);
			}
			log.put("requests", reqs);
			
			try (FileWriter file = new FileWriter("log_v"+currentVersion+".json")) {
	            file.write(log.toJSONString());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
			return true;
		}
		
		//Display all requests made so far
		@GetMapping("/currency-converter/show-requests")
		public List<Request> showRequests() {
			return requests.getRequests();
		}
		
		
		//Get this from the database
		@GetMapping("/currency-converter/show-logs")
		public List<Request> showLogs() {
			logList = requestRepository.findAll();
			return logList;
		}
		
		
		
		
		
		
		
		
		
		
		
		public CurrencyConversionController() {
			
		}
		

			
			
		
}
