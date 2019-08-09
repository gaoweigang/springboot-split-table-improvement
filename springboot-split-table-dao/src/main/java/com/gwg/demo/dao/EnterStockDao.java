package com.gwg.demo.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gwg.demo.model.ZtoEnterStock;

@Component
public interface EnterStockDao {
	
	public List<ZtoEnterStock> selectEnterStockByDepotCodeAndBillCode(String depotCode, String billCode) throws Exception;

	public boolean insertEnterStockInfo(ZtoEnterStock record) throws Exception;

}
