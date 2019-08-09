package com.gwg.orm.exception;

/**
 * <p>Class: ErrorCode</p>
 * <p>Description: 异常Code的接口，用于标准化异常Enum的必要实现方法</p>
 */
public interface ErrorCode {

    public String getCode();
    public String getMessage();

}
