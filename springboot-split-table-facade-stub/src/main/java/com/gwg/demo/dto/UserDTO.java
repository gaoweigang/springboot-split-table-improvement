package com.gwg.demo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable{

    private String userId;//用户ID

    private String userName;//用户名称


}
