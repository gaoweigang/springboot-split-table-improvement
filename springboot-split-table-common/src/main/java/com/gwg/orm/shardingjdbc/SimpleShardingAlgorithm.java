package com.gwg.orm.shardingjdbc;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;
import com.gwg.orm.context.RequestContext;
import com.gwg.orm.properties.TableConfig;
import org.apache.commons.lang3.StringUtils;

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

        /**
         * 找库： userId后四位mod 4分到4个库
         * 问题：为什么可以使用userId后4位作为分库分表的键
         * 解释：因为订单号生成规则中有用户ID后4位，用户只能看到自己的订单号，如果用户输入错误订单号，则
         */
        @Override
        public String doEqualSharding(Collection<String> collection, ShardingValue<Long> shardingValue) {
            String userId = RequestContext.getOrCreate().getUserDTO().getUserId();
            String userIdSub = StringUtils.substring(userId, userId.length() -4 , userId.length());
            Long ds = Long.valueOf(userIdSub) % collection.size();

            return ShardingJbdcUtil.generationCurrentDataBaseName(ds);
        }

        @Override
        public Collection<String> doInSharding(Collection<String> collection, ShardingValue<Long> shardingValue) {
            Set<String> dbNameSet = new HashSet<>();
            String dbName = doEqualSharding(collection, shardingValue);
            dbNameSet.add(dbName);
            return dbNameSet;
        }

        @Override
        public Collection<String> doBetweenSharding(Collection<String> collection, ShardingValue<Long> shardingValue) {
            Set<String> dbNameSet = new HashSet<>();
            String dbName = doEqualSharding(collection, shardingValue);
            dbNameSet.add(dbName);
            return dbNameSet;
        }
    }

    class TableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Long> {

        /**
         * 找表： userId后四位div 4 mod 4， 在这里为什么要div 4,为了让尽可能多的位数参与运算
         *
         * 这种方案是用户只能查询自己的订单，如果查询所有的订单，则应该从订单号中截取用户ID后4位来处理
         */
        @Override
        public String doEqualSharding(Collection<String> collection, ShardingValue<Long> shardingValue) {

            String userId = RequestContext.getOrCreate().getUserDTO().getUserId();
            String userIdSub = StringUtils.substring(userId, userId.length() -4 , userId.length());
            Long moduloValue = (Long.valueOf(userIdSub) / collection.size()) % collection.size();
            return ShardingJbdcUtil.generationCurrentTableName(shardingValue.getLogicTableName(),Long.parseLong(collection.size()+""),moduloValue,tableConfig.getFormat());
        }

        @Override
        public Collection<String> doInSharding(Collection<String> collection, ShardingValue<Long> shardingValue) {
            Set<String> tableNameSet = new HashSet<>();
            String tableName = doEqualSharding(collection, shardingValue);
            tableNameSet.add(tableName);
            return tableNameSet;
        }

        @Override
        public Collection<String> doBetweenSharding(Collection<String> collection, ShardingValue<Long> shardingValue) {
            Set<String> tableNameSet = new HashSet<>();
            String tableName = doEqualSharding(collection, shardingValue);
            tableNameSet.add(tableName);
            return tableNameSet;
        }
    }

}

