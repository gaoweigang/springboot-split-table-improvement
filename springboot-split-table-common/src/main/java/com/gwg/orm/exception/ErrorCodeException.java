/**
 * <p>Title: ErrorCodeException.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: www.zto.com</p>
 */
package com.gwg.orm.exception;

import java.text.MessageFormat;

/**
 * <p>Class: ErrorCodeException</p>
 * <p>Description: 提供一个传入ErrorCode来构建异常的类,ErrorCode实现参考ExceptionEnum</p>
 */
public class ErrorCodeException extends BaseException {
    public ErrorCodeException(ErrorCode code, Object... args) {
        super(code.getCode(), MessageFormat.format(code.getMessage(),args));
    }


    public ErrorCodeException(Throwable e,ErrorCode code, Object... args) {
        super(code.getCode(), MessageFormat.format(code.getMessage(),args),e);
    }
}
