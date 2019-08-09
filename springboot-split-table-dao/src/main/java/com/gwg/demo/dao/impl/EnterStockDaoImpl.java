package com.gwg.demo.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.gwg.demo.dao.EnterStockDao;
import com.gwg.demo.mapper.ZtoEnterStockMapper;
import com.gwg.demo.model.ZtoEnterStock;

@Component
public class EnterStockDaoImpl implements EnterStockDao{
	
	
	private static final Logger LOG = LoggerFactory.getLogger(EnterStockDao.class);
	
	@Autowired
	private ZtoEnterStockMapper enterStockMapper;
	
	public List<ZtoEnterStock> selectEnterStockByDepotCodeAndBillCode(String depotCode, String billCode) throws Exception{
		LOG.info("查询条件,形象店编号："+depotCode+"， 运单号："+billCode);
		Example example = new Example(ZtoEnterStock.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("depotCode", depotCode);
		criteria.andEqualTo("billCode", billCode);
		List<ZtoEnterStock> enterStockList =  enterStockMapper.selectByExample(example);
		return enterStockList;
	}

	@Override
	public boolean insertEnterStockInfo(ZtoEnterStock record) throws Exception {
		return enterStockMapper.insert(record) > 0;
	}
}
