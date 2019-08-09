/**
 * <p>Title: TableConfig.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: www.zto.com</p>
 */
package com.gwg.orm.properties;

/**
 * <p>Class: TableConfig</p>
 * <p>Description: 分库分表规则的定义</p>
 */
public class TableConfig {
    private String name;
    private String shardingColumn;
    private Long size;
    private String strategy;
    private String format;

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShardingColumn() {
        return shardingColumn;
    }

    public void setShardingColumn(String shardingColumn) {
        this.shardingColumn = shardingColumn;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
