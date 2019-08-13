package com.gwg.demo.interceptor;

import com.gwg.orm.context.RequestContext;
import com.gwg.orm.model.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 模拟用户登录的时候会从redis取session
 *
 */
@Slf4j
@Component
public class GenericRequestInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //根据token从redis中获取用户信息
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId("526842");
        userDTO.setUserName("gaoweigang");
        RequestContext ctxt = RequestContext.getOrCreate();
        ctxt.setUserDTO(userDTO);
        return true;
    }
}
