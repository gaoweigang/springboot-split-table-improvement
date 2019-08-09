package com.gwg.demo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable{

    private Integer userCode;//用户编码

    private String userName;//用户名称


}
