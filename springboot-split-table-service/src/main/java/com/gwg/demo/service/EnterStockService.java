package com.gwg.demo.service;

import java.util.List;

import com.gwg.demo.model.ZtoEnterStock;
import com.gwg.demo.request.EnterStockRequest;

public interface EnterStockService {
	
	public List<ZtoEnterStock> queryEnterStockByDepotCodeAndBillCode(EnterStockRequest request) throws Exception;

	public void addEnterStockInfo(EnterStockRequest request) throws Exception;
}
