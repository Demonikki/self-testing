package com.microservices.currencyconversionservice;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class RequestLog {

	@Id
	@GeneratedValue
	private Integer LogId;
	
	@OneToMany(mappedBy="log")
	private List<Request> requests;
	
	
	
	protected RequestLog(){
		requests = new ArrayList<>();
	}
	

	
	
	public List<Request> getRequests() {
		return requests;
	}




	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}




	public Integer getLogId() {
		return LogId;
	}
	public void setLogId(Integer logId) {
		LogId = logId;
	}
	
	public RequestLog(Integer logId, List<Request> reqs) {
		super();
		LogId = logId;
		requests = reqs;
	}
	
	public void addRequest(Request r) {
		requests.add(r);
	}
	
	@Override
	public String toString() {
		return "RequestLog [LogId=" + LogId + ", logs=" + requests + "]";
	}
	
	
}
