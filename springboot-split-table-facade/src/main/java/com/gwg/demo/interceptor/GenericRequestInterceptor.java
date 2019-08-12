package com.gwg.demo.interceptor;

import com.gwg.demo.dto.UserDTO;
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
        //从redis中获取Session
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId("526842");
        userDTO.setUserName("gaoweigang");
        RequestContext ctxt = RequestContext.getOrCreate();
        ctxt.setUserDTO(userDTO);
        return true;
    }
}
