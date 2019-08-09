/**
 * <p>Title: ShardingJdbcConfiguration.java</p>
 */
package com.gwg.orm.configuration;

import com.github.pagehelper.PageInterceptor;
import com.gwg.orm.env.EnvironmentManager;
import com.gwg.orm.exception.BusinessException;
import com.gwg.orm.help.DBPasswordDecoder;
import com.gwg.orm.help.ResourceHelp;
import com.gwg.orm.properties.MyBatisProperties;
import com.gwg.orm.properties.TableConfig;
import com.gwg.orm.shardingjdbc.ShardingJbdcUtil;
import com.gwg.orm.shardingjdbc.SimpleShardingDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import javax.sql.DataSource;

import java.util.*;

/**
 * <p>Class: ShardingJdbcConfiguration</p>
 * <p>Description: 分库分表相关的配置信息</p>
 */

@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class,DataSourceTransactionManagerAutoConfiguration.class})
public class ShardingJdbcConfiguration implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {
    private static Logger logger = LoggerFactory.getLogger(ShardingJdbcConfiguration.class);
    private String defaultDataSource;
    private List<MyBatisProperties> dataSources;
    private String mapperLocations;
    private String typeAliasesPackage;
    private String basePackage;
    private String markerInterface;
    private List<TableConfig> tableConfigs;
    private ConfigurableEnvironment env;

    private DataSource createDataSource(MyBatisProperties config) {
        HikariConfig hikariConfig = new HikariConfig();
        if(StringUtils.isNotEmpty(config.getName())) {
            hikariConfig.setPoolName(config.getName());
        }
        if(config.getMaximumPoolSize()!=null) {
            hikariConfig.setMaximumPoolSize(config.getMaximumPoolSize());
        }
        if(StringUtils.isNotEmpty(config.getDataSourceClassName())) {
            hikariConfig.setDataSourceClassName(config.getDataSourceClassName());
        }
        if(StringUtils.isNotEmpty(config.getDriverClassName())){
            hikariConfig.setDriverClassName(config.getDriverClassName());
        }
        if(config.getMinimumIdle()!=null){
            hikariConfig.setMinimumIdle(config.getMinimumIdle());
        }
        if(config.getConnectionTimeout()!=null){
            hikariConfig.setConnectionTimeout(config.getConnectionTimeout());
        }
        if(config.getIdleTimeout()!=null){
            hikariConfig.setIdleTimeout(config.getIdleTimeout());
        }
        if(config.getValidationQuery()!=null){
            hikariConfig.setConnectionTestQuery(config.getValidationQuery());
        }
        if(config.getMaxLifetime()!=null){
            hikariConfig.setMaxLifetime(config.getMaxLifetime());
        }

        if(StringUtils.isNotEmpty(config.getJdbcUrl())) {
            hikariConfig.setJdbcUrl(config.getJdbcUrl());
        }
        if(StringUtils.isNotEmpty(config.getUsername())) {
            hikariConfig.setUsername(config.getUsername());
        }
        if(StringUtils.isNotEmpty(config.getPassword())){
            hikariConfig.setPassword(DBPasswordDecoder.decode(config.getPassword()));
        }
        return new HikariDataSource(hikariConfig);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanFactory) throws BeansException {
        initConfig();
        String dataSourceName = "shardingDataSource";
        String sessionFactoryName = "shardingSessionFactory";
        String mapperScannerConfigurerName = "shardingMapperScannerConfigurer";
        String transactionManagerName ="shardingTransactionManager";
        String transactionTemplateName="shardingTransactionTemplate";
        try {
            registerDataSourceBeanDefinitionBuilder(dataSourceName, beanFactory, dataSources);
        } catch (Exception e) {
            throw BusinessException.getInstance(e,"创建数据库连接池异常{0}",dataSourceName);
        }
        registerSessionFactoryDefinitionBuilder(sessionFactoryName, beanFactory,dataSourceName);
        registerMapperScannerDefinitionBuilder(mapperScannerConfigurerName, beanFactory,sessionFactoryName);
        registerTransactionManagerDefinitionBuilder(transactionManagerName, beanFactory, dataSourceName);
        registerTransactionTemplateDefinitionBuilder(transactionTemplateName,beanFactory,transactionManagerName);
    }


    private void registerTransactionManagerDefinitionBuilder(String transactionManagerName, BeanDefinitionRegistry beanFactory, String dataSourceName) {
        BeanDefinitionBuilder transactionManagerDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(DataSourceTransactionManager.class);
        transactionManagerDefinitionBuilder.addPropertyReference("dataSource", dataSourceName);
        beanFactory.registerBeanDefinition(transactionManagerName, transactionManagerDefinitionBuilder.getRawBeanDefinition());

    }

    private void registerMapperScannerDefinitionBuilder(String mapperScannerConfigurerName, BeanDefinitionRegistry beanFactory, String sqlSessionFactoryName) {
        BeanDefinitionBuilder mapperScannerDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(MapperScannerConfigurer.class);
        mapperScannerDefinitionBuilder.addPropertyValue("basePackage", this.basePackage);
        mapperScannerDefinitionBuilder.addPropertyReference("sqlSessionFactory", sqlSessionFactoryName);
        if(this.markerInterface!=null) {
            mapperScannerDefinitionBuilder.addPropertyValue("markerInterface", this.markerInterface);
        }
        beanFactory.registerBeanDefinition(mapperScannerConfigurerName, mapperScannerDefinitionBuilder.getBeanDefinition());

    }

    private void registerSessionFactoryDefinitionBuilder(String sessionFactoryName, BeanDefinitionRegistry beanFactory, String dataSourceName) {
        BeanDefinitionBuilder sessionFactoryDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(SqlSessionFactoryBean.class);
        sessionFactoryDefinitionBuilder.addPropertyValue("mapperLocations", ResourceHelp.resolveMapperLocations(this.mapperLocations));
        sessionFactoryDefinitionBuilder.addPropertyValue("typeAliasesPackage", this.typeAliasesPackage);
        sessionFactoryDefinitionBuilder.addPropertyReference("dataSource", dataSourceName);

        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("autoRuntimeDialect", "true");
        pageInterceptor.setProperties(properties);

        

        /*
        //Cat配置
        List<Interceptor> mybatisInterceptors = new ArrayList<Interceptor>();
        mybatisInterceptors.add(pageInterceptor);
        
        Set<String> mybatisPlugins = PluginConfigManager.getPropertyValueSet("org.apache.ibatis.plugin.Interceptor");
        mybatisPlugins.forEach(mybatisPlugin -> {
            try {
                Class pluginClass = Class.forName(mybatisPlugin);
                Interceptor mybatisInterceptor = (Interceptor) pluginClass.newInstance();
                mybatisInterceptors.add(mybatisInterceptor);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                logger.error("load mybatis plugin error: ", e);
            }
        });

        sessionFactoryDefinitionBuilder.addPropertyValue("plugins", mybatisInterceptors);*/
        beanFactory.registerBeanDefinition(sessionFactoryName, sessionFactoryDefinitionBuilder.getRawBeanDefinition());

    }

    private void registerDataSourceBeanDefinitionBuilder(String dataSourceName, BeanDefinitionRegistry beanFactory, List<MyBatisProperties> dataSources) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        BeanDefinitionBuilder dataSourceDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(SimpleShardingDataSource.class);
        Map<String, DataSource> dataSourceMap = new HashMap<>(dataSources.size());
        for(MyBatisProperties config:dataSources){
            dataSourceMap.put(config.getName(),createDataSource(config));
        }
        dataSourceDefinitionBuilder.addPropertyValue("dataSourceMap",dataSourceMap);
        dataSourceDefinitionBuilder.addPropertyValue("defaultDataSource",defaultDataSource);
        dataSourceDefinitionBuilder.addPropertyValue("tableConfigs",this.tableConfigs);
        dataSourceDefinitionBuilder.setDestroyMethodName("shutdown");
        dataSourceDefinitionBuilder.setInitMethodName("init");
        beanFactory.registerBeanDefinition(dataSourceName, dataSourceDefinitionBuilder.getRawBeanDefinition());

    }

    private void registerTransactionTemplateDefinitionBuilder(String transactionTemplateName,BeanDefinitionRegistry beanFactory,String transactionManagerName ){
        BeanDefinitionBuilder beanDefinitionBuilder=BeanDefinitionBuilder.genericBeanDefinition(TransactionTemplate.class);
        beanDefinitionBuilder.addPropertyReference("transactionManager",transactionManagerName);
        beanFactory.registerBeanDefinition(transactionTemplateName,beanDefinitionBuilder.getRawBeanDefinition());
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }


    private void initConfig() {
        this.basePackage=EnvironmentManager.getProperty(env,EnvironmentManager.SHARDING_BASEPACKAGE);
        String defaultDSIndex = EnvironmentManager.getProperty(env,EnvironmentManager.SHARDING_DEFATULT_DS_INDEX);
        if(StringUtils.isNumeric(defaultDSIndex)){
            this.defaultDataSource=ShardingJbdcUtil.generationCurrentDataBaseName(Long.parseLong(defaultDSIndex));
        }else {
            this.defaultDataSource =ShardingJbdcUtil.generationCurrentDataBaseName(0L);
        }
        this.mapperLocations=EnvironmentManager.getProperty(env,EnvironmentManager.SHARDING_MAPPERLOCATIONS);
        this.typeAliasesPackage=EnvironmentManager.getProperty(env,EnvironmentManager.SHARDING_TYPEALIASESPACKAGE);
        this.markerInterface=EnvironmentManager.getProperty(env,EnvironmentManager.SHARDING_MARKERINTERFACE);
        int index = 0;
        List<MyBatisProperties> configList = new ArrayList<>();
        while (true) {
            if (StringUtils.isNotEmpty(EnvironmentManager.getProperty(env,String.format(EnvironmentManager.SHARDING_DATASOURCES_JDBCURL, index)))) {
                configList.add(index,new MyBatisProperties());
                MyBatisProperties config = configList.get(index);
                config.setName(ShardingJbdcUtil.generationCurrentDataBaseName(Long.valueOf(index)));
                config.setDriverClassName(EnvironmentManager.getProperty(env,String.format(EnvironmentManager.SHARDING_DATASOURCES_DRIVERCLASSNAME, index)));
                config.setJdbcUrl(EnvironmentManager.getProperty(env,String.format(EnvironmentManager.SHARDING_DATASOURCES_JDBCURL, index)));
                config.setUsername(EnvironmentManager.getProperty(env,String.format(EnvironmentManager.SHARDING_DATASOURCES_USERNAME, index)));
                config.setPassword(EnvironmentManager.getProperty(env,String.format(EnvironmentManager.SHARDING_DATASOURCES_PASSWORD, index)));
                String readOnlyStr = EnvironmentManager.getProperty(env,String.format(EnvironmentManager.SHARDING_DATASOURCES_READONLY, index));
                if (StringUtils.isNotEmpty(readOnlyStr)) {
                    config.setReadOnly(Boolean.parseBoolean(readOnlyStr.trim()));
                }
                String connectionTimeoutStr = EnvironmentManager.getProperty(env,String.format(EnvironmentManager.SHARDING_DATASOURCES_CONNECTIONTIMEOUT, index));
                if (StringUtils.isNotEmpty(connectionTimeoutStr)) {
                    config.setConnectionTimeout(Long.parseLong(connectionTimeoutStr));
                }
                String idleTimeoutStr = EnvironmentManager.getProperty(env,String.format(EnvironmentManager.SHARDING_DATASOURCES_IDLETIMEOUT, index));
                if (StringUtils.isNotEmpty(idleTimeoutStr)) {
                    config.setIdleTimeout(Long.parseLong(idleTimeoutStr));
                }
                String maxLifetimeStr = EnvironmentManager.getProperty(env,String.format(EnvironmentManager.SHARDING_DATASOURCES_MAXLIFETIME, index));
                if (StringUtils.isNotEmpty(maxLifetimeStr)) {
                    config.setMaxLifetime(Long.parseLong(maxLifetimeStr));
                }
                String maxImumpoolSizeStr = EnvironmentManager.getProperty(env,String.format(EnvironmentManager.SHARDING_DATASOURCES_MAXIMUMPOOSIZE, index));
                if (StringUtils.isNotEmpty(maxImumpoolSizeStr)) {
                    config.setMaximumPoolSize(Integer.parseInt(maxImumpoolSizeStr));
                }
                String minimumIdleStr = EnvironmentManager.getProperty(env,String.format(EnvironmentManager.SHARDING_DATASOURCES_MINIMUMIDLE, index));
                if (StringUtils.isNotEmpty(minimumIdleStr )) {
                    config.setMinimumIdle(Integer.parseInt(minimumIdleStr ));
                }
                String dataSourceClassName = EnvironmentManager.getProperty(env,String.format(EnvironmentManager.SHARDING_DATASOURCES_DATASOURCECLASSNAME, index));
                if(StringUtils.isNotEmpty(dataSourceClassName)){
                    config.setDataSourceClassName(dataSourceClassName);
                }
                String validationQuery = EnvironmentManager.getProperty(env,String.format(EnvironmentManager.SHARDING_DATASOURCES_VALIDATIONQUERY, index));
                if(StringUtils.isNotEmpty(validationQuery)){
                    config.setValidationQuery(validationQuery);
                }

            } else {
                break;
            }
            index++;
        }
        index = 0;
        List<TableConfig> tableConfigList = new ArrayList<>();
        while (true) {
            String tableName = EnvironmentManager.getProperty(env,String.format(EnvironmentManager.SHARDING_TABLECONFIGS_NAME, index));
            if (StringUtils.isNotEmpty(tableName)) {
                tableConfigList.add(index,new TableConfig());
                TableConfig config = tableConfigList.get(index);
                config.setName(tableName);
                config.setShardingColumn(EnvironmentManager.getProperty(env,String.format(EnvironmentManager.SHARDING_TABLECONFIGS_CLOUMN, index)));
                String tableSizeStr = EnvironmentManager.getProperty(env,String.format(EnvironmentManager.SHARDING_TABLECONFIGS_SIZE, index));
                config.setSize(Long.parseLong(tableSizeStr));
                config.setStrategy(EnvironmentManager.getProperty(env,String.format(EnvironmentManager.SHARDING_TABLECONFIGS_STRATEGY,index)));
                config.setFormat(EnvironmentManager.getProperty(env,String.format(EnvironmentManager.SHARDING_TABLECONFIGS_FORMAT,index)));
            }else{
                break;
            }
            index++;
        }
        if(!CollectionUtils.isEmpty(tableConfigList)){
            this.tableConfigs = tableConfigList;
        }

        if(!CollectionUtils.isEmpty(configList)){
            this.dataSources = configList;
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.env = (ConfigurableEnvironment) environment;
    }
}

