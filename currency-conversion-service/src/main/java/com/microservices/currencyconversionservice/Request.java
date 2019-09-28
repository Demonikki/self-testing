package com.microservices.currencyconversionservice;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;



@Entity
public class Request {
//	
	@Id
	@GeneratedValue
	private Integer requestId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private RequestLog log;
	
	@Column(name="currency_from")
	private String from;
	@Column(name="currency_to")
	private String to;
	private BigDecimal quantity;

	//Response field
	private BigDecimal conversionMultiple;
	private BigDecimal total;
	
	
	public Request() {
		
	}


	public Request(String from, String to, BigDecimal quantity,BigDecimal conversionMultiple, BigDecimal total) {
		super();
		this.from = from;
		this.to = to;
		this.quantity = quantity;
		this.total = total;
		this.conversionMultiple = conversionMultiple;
	}

	public BigDecimal getConversionMultiple() {
		return conversionMultiple;
	}


	public void setConversionMultiple(BigDecimal conversionMultiple) {
		this.conversionMultiple = conversionMultiple;
	}


	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	

	public String getFrom() {
		return from;
	}


	public void setFrom(String from) {
		this.from = from;
	}


	public String getTo() {
		return to;
	}


	public void setTo(String to) {
		this.to = to;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Request [from=" + from + ", to=" + to + ", quantity="
				+ quantity.intValue() + ", total=" + total.intValue() + "]";
	}
	
	@Override
	public boolean equals(Object o){
		final Request r = (Request) o;
		
		if(	r.getFrom().equals(this.getFrom()) 		&&
			r.getTo().equals(this.getTo())			&&
			r.getQuantity().intValue() == this.getQuantity().intValue() 	&&
			r.getTotal().intValue() == this.getTotal().intValue())
			
			return true;
		return false;
	}


}
