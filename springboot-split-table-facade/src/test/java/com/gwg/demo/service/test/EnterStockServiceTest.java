package com.gwg.demo.service.test;

import com.alibaba.fastjson.JSON;
import com.gwg.demo.model.ZtoEnterStock;
import com.gwg.demo.request.AddEnterStockReq;
import com.gwg.demo.request.QueryEnterStockReq;
import com.gwg.demo.service.EnterStockService;
import com.gwg.orm.context.RequestContext;
import com.gwg.orm.model.UserDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 我用的1.5.2版本的，直接用自动生成的两个注解就可以实现测试功能。
 * 在1.5.2版本之前用
 * @RunWith(SpringJUnit4ClassRunner.class)  
 * @SpringApplicationConfiguration(classes = {Application.class}) 
 * 在1.5.2版本之后用
 * @RunWith(SpringRunner.class)
 * @SpringBootTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest 
//相当于  --spring.profiles.active=dev  
@ActiveProfiles(value="dev")
//@EnableTransactionManagement  
public class EnterStockServiceTest {

	//private static final Logger LOG = Logger.getLogger(StudentServiceTest.class);
	
	private static final Logger LOG = LoggerFactory.getLogger(EnterStockServiceTest.class);
	
	@Autowired
	private EnterStockService enterStockService;

	@Before
	public void before(){
        //1.模拟用户已登录
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId("654442");
        userDTO.setUserName("gaoweigang");
        RequestContext.getOrCreate().setUserDTO(userDTO);
    }

	/**
	 * 插入数据
	 */
	@Test
	public void testAddEnterStockInfo() throws Exception{
		AddEnterStockReq request = new AddEnterStockReq();
		request.setBillCode("156566103893044424379");
		request.setDepotCode("ZT39284326400");
		request.setExpressCompanyCode("EMS");
		request.setExpressType(1);
		request.setTakeCode("543216");
		request.setUploadDate(new Date());
		request.setReceiveMan("刘金菊");
		request.setReceiveManMobile("13817191464");

		enterStockService.addEnterStockInfo(request);

	}


	/**
	 * 查询数据
	 */
	@Test
	public void testQueryEnterStockInfoByDepotCodeAndBillCode() throws Exception{
		//用户登录了才能操作
		QueryEnterStockReq request = new QueryEnterStockReq();
		request.setBillCode("156566103893044424370");
		request.setDepotCode("ZT39284326400");
		LOG.info("查询条件："+request);
		List<ZtoEnterStock> enterStockList = enterStockService.queryEnterStockByDepotCodeAndBillCode(request);
	    if(enterStockList != null && enterStockList.size() > 0){
	    	ZtoEnterStock enterStock  =  enterStockList.get(0);
	    	LOG.info("收件人姓名："+enterStock.getReceiveMan());
	 		LOG.info("收件人手机号："+enterStock.getReceiveManMobile());
	    }
	   
		
	}

	/**
	 * 使用in来查询数据
	 */
	@Test
	public void testQueryEnterStockInfoListByBillCode() throws Exception{
		//用户登录了才能操作
		QueryEnterStockReq request = new QueryEnterStockReq();
		List<String> billCodeList = new ArrayList<String>();
		billCodeList.add("156566103893044424370");
		billCodeList.add("156566103893044424371");
		billCodeList.add("156566103893044424379");
		request.setBillCodeList(billCodeList);
		LOG.info("查询条件："+request);
		List<ZtoEnterStock> enterStockList = enterStockService.queryEnterStockListByBillCode(request);
		if(CollectionUtils.isEmpty(enterStockList)){
			return;
		}
		enterStockList.forEach( a -> {
			LOG.info("订单记录： {} ", JSON.toJSONString(a));
		});


	}

	/**
	 * 使用between来查询数据
	 */



}
