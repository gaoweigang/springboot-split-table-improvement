package com.gwg.demo.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.gwg.demo.dao.EnterStockDao;
import com.gwg.demo.request.QueryEnterStockReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwg.demo.model.ZtoEnterStock;
import com.gwg.demo.request.AddEnterStockReq;
import com.gwg.demo.service.EnterStockService;
import com.gwg.orm.util.StringUtil;

@Component
public class EnterStockServiceImpl implements EnterStockService{
	
	private static final Logger LOG = LoggerFactory.getLogger(EnterStockServiceImpl.class);
	
	@Autowired
	private EnterStockDao enterStockDao;

	public List<ZtoEnterStock> queryEnterStockByDepotCodeAndBillCode(QueryEnterStockReq request) throws Exception {
		try {
			if(StringUtil.isEmpty(request, request.getDepotCode(), request.getBillCode())){
                return null;
            }
			LOG.info("请求参数，形象店编码："+request.getDepotCode() + ", 运单号："+request.getBillCode());
			List<ZtoEnterStock> enterStockList = enterStockDao.selectEnterStockByDepotCodeAndBillCode(request.getDepotCode(), request.getBillCode());
			LOG.info("返回结果："+enterStockList.size());
			return enterStockList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<ZtoEnterStock> queryEnterStockListByBillCode(QueryEnterStockReq request) throws Exception{
		try {
			if(StringUtil.isEmpty(request.getBillCodeList())){
				return null;
			}
			LOG.info("请求参数, 运单号：{}", JSON.toJSONString(request.getBillCodeList()));
			List<ZtoEnterStock> enterStockList = enterStockDao.selectEnterStockListByBillCode(request.getBillCodeList());
			LOG.info("返回结果："+enterStockList.size());
			return enterStockList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



	@Override
	public void addEnterStockInfo(AddEnterStockReq request) throws Exception {

		try {
			ZtoEnterStock enterStock = new ZtoEnterStock();
			enterStock.setDepotCode(request.getDepotCode());
			enterStock.setBillCode(request.getBillCode());
			enterStock.setExpressCompanyCode(request.getExpressCompanyCode());
			enterStock.setExpressType(request.getExpressType());
			enterStock.setTakeCode(request.getTakeCode());
			enterStock.setUploadDate(request.getUploadDate());
			enterStock.setReceiveMan(request.getReceiveMan());
			enterStock.setReceiveManMobile(request.getReceiveManMobile());

			if(enterStockDao.insertEnterStockInfo(enterStock)){
                System.out.println("插入数据库成功");
                return;
            }
			System.out.println("插入数据库失败");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
