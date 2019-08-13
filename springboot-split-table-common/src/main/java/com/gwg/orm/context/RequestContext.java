package com.gwg.orm.context;

import com.gwg.orm.model.UserDTO;
import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求上下文
 */
@Data
public class RequestContext {
    private static final Log LOG = LogFactory.getLog(RequestContext.class);

    private static final ThreadLocal<RequestContext> REQUEST_CONTEXT = ThreadLocalManager.createThreadLocal(RequestContext.class);

    public synchronized static RequestContext getOrCreate() {
        RequestContext instance = REQUEST_CONTEXT.get();
        if (null == instance) {
            instance = new RequestContext();
            REQUEST_CONTEXT.set(instance);
        }
        return instance;
    }


    protected HttpServletRequest request;

    protected HttpServletResponse response;

    protected UserDTO userDTO;



}
