package com.gwg.orm.help;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.gwg.orm.exception.ErrorCodeException;
import com.gwg.orm.exception.ExceptionEnum;

/**
 * <p>Class: ResourceHelp</p>
 * <p>Description: 用户搜索Mybatis文件的辅助类</p>
 */
public class ResourceHelp {
    public static Resource[] resolveMapperLocations(String mapperLocations) {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        List<Resource> resources = new ArrayList<Resource>();
        if (mapperLocations != null) {
            String[] mapperLocationArray = mapperLocations.split(",");
            for (String mapperLocation : mapperLocationArray) {
                try {
                    Resource[] mappers = resourceResolver.getResources(mapperLocation);
                    resources.addAll(Arrays.asList(mappers));
                } catch (IOException e) {
                   throw new ErrorCodeException(e,ExceptionEnum.FILE_EXCEPTION,"Mybatis搜索资源文件",mapperLocation);
                }
            }
        }
        return resources.toArray(new Resource[resources.size()]);
    }
}
