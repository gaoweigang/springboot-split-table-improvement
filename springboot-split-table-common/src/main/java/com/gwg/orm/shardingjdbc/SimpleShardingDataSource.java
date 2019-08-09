package com.gwg.orm.shardingjdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.jdbc.ShardingDataSource;
import com.gwg.orm.properties.TableConfig;
import com.gwg.orm.sequence.AbstractLifecycle;

public class SimpleShardingDataSource extends AbstractLifecycle implements DataSource {
    private Map<String, DataSource> dataSourceMap;
    private String defaultDataSource;
    private List<TableConfig> tableConfigs;
    private DataSource dataSource;
    public void doInit(){
        DataSourceRule dataSourceRule = new DataSourceRule(dataSourceMap, defaultDataSource);
        List<TableRule> tableRuleList = new ArrayList<>(tableConfigs.size());
        for (TableConfig config : tableConfigs){
            List<String> tableNames = ShardingJbdcUtil.generationTableNames(config.getName(), config.getSize(),config.getFormat());
            SimpleShardingAlgorithm simpleShardingAlgorithm = new SimpleShardingAlgorithm(config);
            TableRule tableRule = TableRule.builder(config.getName())
                    .actualTables(tableNames)
                    .dataSourceRule(dataSourceRule)
                    .databaseShardingStrategy(new DatabaseShardingStrategy(config.getShardingColumn(), simpleShardingAlgorithm.getDataBaseShardingAlgorithm()))
                    .tableShardingStrategy(new TableShardingStrategy(config.getShardingColumn(),simpleShardingAlgorithm.getTableShardingAlgorithm()))
                    .build();
            tableRuleList.add(tableRule);
        }
        ShardingRule shardingRule = ShardingRule.builder().dataSourceRule(dataSourceRule).tableRules(tableRuleList).build();
        dataSource = ShardingDataSourceFactory.createDataSource(shardingRule);
    }
    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return dataSource.getConnection(username,password);
    }
    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return dataSource.unwrap(iface);
    }
    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return dataSource.isWrapperFor(iface);
    }
    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return dataSource.getLogWriter();
    }
    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        this.dataSource.setLogWriter(out);
    }
    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        this.dataSource.setLoginTimeout(seconds);
    }
    @Override
    public int getLoginTimeout() throws SQLException {
        return this.dataSource.getLoginTimeout();
    }
    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return this.dataSource.getParentLogger();
    }
    public void shutdown() {
        if(this.dataSource instanceof ShardingDataSource){
            ((ShardingDataSource)dataSource).shutdown();
        }
    }
    public List<TableConfig> getTableConfigs() {
        return tableConfigs;
    }
    public void setTableConfigs(List<TableConfig> tableConfigs) {
        this.tableConfigs = tableConfigs;
    }
    public Map<String, DataSource> getDataSourceMap() {
        return dataSourceMap;
    }

    public void setDataSourceMap(Map<String, DataSource> dataSourceMap) {
        this.dataSourceMap = dataSourceMap;
    }

    public String getDefaultDataSource() {
        return defaultDataSource;
    }

    public void setDefaultDataSource(String defaultDataSource) {
        this.defaultDataSource = defaultDataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}