package com.gwg.orm.exception;

/**
 * <p>Class: BusinessException</p>
 * <p>Description: 通用的业务异常类</p>
 */
public class BusinessException extends BaseException {

    public static final String ERR_DEF="DEF001";

    /**
     * 传入code 和Msg 构建BusinessException对象
     * @param code
     * @param msg
     */
    public BusinessException(String code, String msg) {
        super(code,msg);
    }

    /***
     * 提供一个简便的构建BusinessException对象方法
     * @param patterm  异常信息模板 如 xxxx {} xxxx
     * @param args  传入模板所需要的参数
     * @return
     */
    public static BusinessException getInstance(String patterm, Object... args) {
        String msg = java.text.MessageFormat.format(patterm,args);
        return new BusinessException(ERR_DEF,msg);
    }

    /***
     * 提供一个简便的构建BusinessException带Exception的对象方法
     * @param e    异常参数
     * @param patterm  异常信息模板 如 xxxx {} xxxx
     * @param args 传入模板所需要的参数
     * @return
     */
    public static BusinessException getInstance(Throwable e,String patterm, Object... args) {
        String msg = java.text.MessageFormat.format(patterm,args);
        return new BusinessException(ERR_DEF,msg,e);
    }

    public BusinessException(String code, String msg,Throwable e) {
        super(code,msg,e);
    }
    public BusinessException(Throwable e,String msg) {
        super(ERR_DEF,msg,e);
    }
    public BusinessException(Throwable e,String msg,Object... objects) {

        super(ERR_DEF,msg,e);
    }
}
