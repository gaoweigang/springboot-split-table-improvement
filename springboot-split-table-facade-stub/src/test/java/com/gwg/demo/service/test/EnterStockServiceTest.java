package com.gwg.demo.service.test;

import com.gwg.demo.model.ZtoEnterStock;
import com.gwg.demo.request.EnterStockRequest;
import com.gwg.demo.service.EnterStockService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

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

	/**
	 * 插入数据
	 */
	@Test
	public void testAddEnterStockInfo() throws Exception{
		EnterStockRequest request = new EnterStockRequest();
		request.setId(1l);
		request.setBillCode("445485670653");
		request.setDepotCode("ZT39284326400");
		request.setExpressCompanyCode("EMS");
		request.setExpressType(1);
		request.setTakeCode("123456");
		request.setUploadDate(new Date());
		request.setReceiveMan("gaoweigang");
		request.setReceiveManMobile("13817191469");

		enterStockService.addEnterStockInfo(request);

	}


	/**
	 * 查询数据
	 */
	@Test
	public void testQueryEnterStockInfoByDepotCodeAndBillCode() throws Exception{
		EnterStockRequest request = new EnterStockRequest();
		request.setBillCode("445485670653");
		request.setDepotCode("ZT39284326400");
		LOG.info("查询条件："+request);
		List<ZtoEnterStock> enterStockList = enterStockService.queryEnterStockByDepotCodeAndBillCode(request);
	    if(enterStockList != null && enterStockList.size() > 0){
	    	ZtoEnterStock enterStock  =  enterStockList.get(0);
	    	LOG.info("收件人姓名："+enterStock.getReceiveMan());
	 		LOG.info("收件人手机号："+enterStock.getReceiveManMobile());
	    }
	   
		
	}
	
	

}
