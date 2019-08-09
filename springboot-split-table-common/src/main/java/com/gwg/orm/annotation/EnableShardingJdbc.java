package com.gwg.orm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.gwg.orm.autoconfigure.EnableShardingJdbcImportSelector;

/**
 * <p>Class: EnableShardingJdbc</p>
 * <p>Description: 开启分库分表自动配置</p>
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({EnableShardingJdbcImportSelector.class})
public @interface EnableShardingJdbc {

}
