package com.gwg.demo.service;

import java.util.List;

import com.gwg.demo.model.ZtoEnterStock;
import com.gwg.demo.request.AddEnterStockReq;
import com.gwg.demo.request.QueryEnterStockReq;

public interface EnterStockService {
	
	public List<ZtoEnterStock> queryEnterStockByDepotCodeAndBillCode(QueryEnterStockReq request) throws Exception;

	public void addEnterStockInfo(AddEnterStockReq request) throws Exception;

	public List<ZtoEnterStock> queryEnterStockListByBillCode(QueryEnterStockReq request) throws Exception;
}
