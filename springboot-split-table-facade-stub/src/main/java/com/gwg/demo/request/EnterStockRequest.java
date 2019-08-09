package com.gwg.demo.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class EnterStockRequest implements Serializable{

	private Long id;
	
	private String depotCode;
	
	private String billCode;

	private String takeCode;

	private Date uploadDate;

	private String expressCompanyCode;

	private Integer expressType;

	private String receiveMan;

	private String receiveManMobile;

	private String receiveManAddr;


}
