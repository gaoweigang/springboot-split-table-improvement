package com.gwg.demo;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gwg.demo.model.ZtoEnterStock;
import com.gwg.orm.annotation.EnableShardingJdbc;
import com.gwg.demo.request.EnterStockRequest;
import com.gwg.demo.service.EnterStockService;
/**
 * @author gaoweigang
 * 
 * 我们的Application类上使用的第一个注解是@RestController。这被称为一个构造型注解。
 * 它为阅读代码的人们提供建议。对于Spring,该类扮演了一个特殊角色。在本示例中，我们的
 * 类是一个web @Controller,所以当处理进来的web请求时，Spring会询问他.
 * 
 * @SpringBootApplication 等价于 @Configuration, @EnableAntoConfiguration 和 @ComponentScan。
 *
 * 在这里配置项目中的数据源禁止自动配置
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class})
@EnableShardingJdbc
public class Application{
	
	@Autowired
	private EnterStockService enterStockService;
	/**
	 * @throws Exception 
	 * @RequestMapping注解提供路由信息。它告诉Spring任何来自"/"路径的请求都应该被映射到home方法。
	 * 
	 * 在这里编写一个测试方法用于测试SpringBoot基础环境有没有配置好，可以在浏览器中输入http://localhost:8080/验证
	 */
	@RequestMapping("/")
	String home() throws Exception{
		EnterStockRequest request = new EnterStockRequest();
		request.setBillCode("445485670653");
		request.setDepotCode("ZT39284326400");
		List<ZtoEnterStock> enterStockList = enterStockService.queryEnterStockByDepotCodeAndBillCode(request);
		return enterStockList.toString();
	}
		
	public static void main(String[] args) throws Exception{
		SpringApplication.run(Application.class, args);
	}

}
