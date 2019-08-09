package com.gwg.orm.sequence;


public interface Lifecycle {
    void init() ;

    void destroy();

    boolean isInited();
}