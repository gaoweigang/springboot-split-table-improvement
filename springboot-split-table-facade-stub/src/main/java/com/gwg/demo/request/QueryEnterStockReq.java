package com.gwg.demo.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QueryEnterStockReq implements Serializable{

    private String depotCode;

    private String billCode;

    private List<String> billCodeList;



}
