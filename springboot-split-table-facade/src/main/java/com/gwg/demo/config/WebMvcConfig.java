package com.gwg.demo.config;

import com.gwg.demo.interceptor.GenericRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Auther: gwg
 * @Date: 2019/7/1 11:18
 * @Description:
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {


    @Autowired
    private GenericRequestInterceptor genericRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(genericRequestInterceptor)
                .excludePathPatterns("/error")
                .addPathPatterns("/**");

    }

}
