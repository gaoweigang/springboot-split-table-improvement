package com.gwg.orm.shardingjdbc;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;
import com.gwg.orm.exception.BusinessException;
import com.gwg.orm.properties.TableConfig;

/**
 * <p>Class: SimpleShardingAlgorithm</p>
 * <p>Description: </p>
 */
public class SimpleShardingAlgorithm {

    public static final String MOD="mod";
    public static final String HASH = "hash";

    private TableConfig tableConfig;
    private DatabaseShardingAlgorithm dataBaseShardingAlgorithm = new DatabaseShardingAlgorithm();
    private TableShardingAlgorithm tableShardingAlgorithm =new TableShardingAlgorithm();
    public SimpleShardingAlgorithm(TableConfig tableConfig){
        this.tableConfig=tableConfig;
    }

    public TableConfig getTableConfig() {
        return tableConfig;
    }

    public void setTableConfig(TableConfig tableConfig) {
        this.tableConfig = tableConfig;
    }

    public DatabaseShardingAlgorithm getDataBaseShardingAlgorithm() {
        return dataBaseShardingAlgorithm;
    }

    public void setDataBaseShardingAlgorithm(DatabaseShardingAlgorithm dataBaseShardingAlgorithm) {
        this.dataBaseShardingAlgorithm = dataBaseShardingAlgorithm;
    }

    public TableShardingAlgorithm getTableShardingAlgorithm() {
        return tableShardingAlgorithm;
    }

    public void setTableShardingAlgorithm(TableShardingAlgorithm tableShardingAlgorithm) {
        this.tableShardingAlgorithm = tableShardingAlgorithm;
    }
    class DatabaseShardingAlgorithm  implements SingleKeyDatabaseShardingAlgorithm<Long>{

        @Override
        public String doEqualSharding(Collection<String> collection, ShardingValue<Long> shardingValue) {
            Object number =shardingValue.getValue();
            Long tableSize = tableConfig.getSize();
            Long value = null;
            if(tableConfig.getStrategy()==null||tableConfig.getStrategy().toLowerCase().equals(MOD)){
                value=Long.parseLong(number.toString());
            }else if(tableConfig.getStrategy().toLowerCase().equals(HASH)){
                value=Long.valueOf(Math.abs(number.hashCode()));
            }else{
                throw BusinessException.getInstance("sharding jdbc 策略设置非法:{}",tableConfig.getStrategy());
            }

            Long size = (tableSize-(tableSize/collection.size()))/(collection.size()-1);
            size = tableSize%size==0?size:size+1;
            Long ds = (value%tableSize)/size;
            return ShardingJbdcUtil.generationCurrentDataBaseName(ds);
        }

        @Override
        public Collection<String> doInSharding(Collection<String> collection, ShardingValue<Long> shardingValue) {
            Set<String> dbNameSet = new HashSet<>();
            for(Object e : shardingValue.getValues()){
                Long value = Long.parseLong(e.toString());
                String dbName = doEqualSharding(collection,new ShardingValue<Long>(shardingValue.getLogicTableName(),shardingValue.getColumnName(),value));
                dbNameSet.add(dbName);
            }
            return dbNameSet;
        }

        @Override
        public Collection<String> doBetweenSharding(Collection<String> collection, ShardingValue<Long> shardingValue) {
            Set<String> dbNameSet = new HashSet<>();
            Range<Long> range = shardingValue.getValueRange();
            Object lowerEndpointNumber =range.lowerEndpoint();
            Object upperEndpointNumber = range.upperEndpoint();
            for (Long i = Long.parseLong(lowerEndpointNumber.toString()); i <= Long.parseLong(upperEndpointNumber.toString()); i++) {
                String dbName = doEqualSharding(collection,new ShardingValue<Long>(shardingValue.getLogicTableName(),shardingValue.getColumnName(),i));
                dbNameSet.add(dbName);
            }
            return dbNameSet;
        }
    }

    class TableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Long> {

        //计算使用那张表
        @Override
        public String doEqualSharding(Collection<String> collection, ShardingValue<Long> shardingValue) {
            Object number = shardingValue.getValue();
            Long tableSize = tableConfig.getSize();
            Long value = null;
            if(tableConfig.getStrategy()==null||tableConfig.getStrategy().toLowerCase().equals(MOD)){
                value=Long.parseLong(number.toString());
            }else if(tableConfig.getStrategy().toLowerCase().equals(HASH)){
                value=Long.valueOf(Math.abs(number.hashCode()));
            }else{
                throw BusinessException.getInstance("sharding jdbc 策略设置非法:{}",tableConfig.getStrategy());
            }
            Long moduloValue = value % tableSize;
            return ShardingJbdcUtil.generationCurrentTableName(shardingValue.getLogicTableName(),Long.parseLong(collection.size()+""),moduloValue,tableConfig.getFormat());
        }

        @Override
        public Collection<String> doInSharding(Collection<String> collection, ShardingValue<Long> shardingValue) {
            Set<String> tableNameSet = new HashSet<>();
            for(Object e : shardingValue.getValues()){
                Long value = Long.parseLong(e.toString());
                String tableName = doEqualSharding(collection,new ShardingValue<Long>(shardingValue.getLogicTableName(),shardingValue.getColumnName(),value));
                tableNameSet.add(tableName);
            }
            return tableNameSet;
        }

        @Override
        public Collection<String> doBetweenSharding(Collection<String> collection, ShardingValue<Long> shardingValue) {
            Set<String> tableNameSet = new HashSet<>();
            Range<Long> range = shardingValue.getValueRange();
            Object lowerEndpointNumber =range.lowerEndpoint();
            Object upperEndpointNumber = range.upperEndpoint();
            for (Long i = Long.parseLong(lowerEndpointNumber.toString()); i <= Long.parseLong(upperEndpointNumber.toString()); i++) {
                String tableName = doEqualSharding(collection,new ShardingValue<Long>(shardingValue.getLogicTableName(),shardingValue.getColumnName(),i));
                tableNameSet.add(tableName);
            }
            return tableNameSet;
        }
    }

}

