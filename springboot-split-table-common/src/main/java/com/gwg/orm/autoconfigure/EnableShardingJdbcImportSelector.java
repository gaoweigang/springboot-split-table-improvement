package com.gwg.orm.autoconfigure;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import com.gwg.orm.configuration.ShardingJdbcConfiguration;

/**
 * <p>Class: EnableShardingJdbcImportSelector</p>
 * <p>Description: 辅助EnableShardingJdbc开启哪些类</p>
 */
public class EnableShardingJdbcImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{ShardingJdbcConfiguration.class.getName()};
    }

}
