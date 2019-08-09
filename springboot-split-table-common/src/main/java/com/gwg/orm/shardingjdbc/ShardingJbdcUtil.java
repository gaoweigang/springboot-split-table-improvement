package com.gwg.orm.shardingjdbc;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Class: ShardingJbdcUtil</p>
 * <p>Description: 分库分表的工具类
 * 1、处理表名的生成
 * 2、获取当前表名
 * 3、生成当前所需的数据库名</p>
 */
public class ShardingJbdcUtil {
    public static final String TABLE_FORMAT="_%04d";
    public static final String DATA_SOURCE_NAME_FORMAT="ds%04d"; //ds + 宽度是将向输出中写入的最少字符数

    public static String generationCurrentDataBaseName(Long index){
        return String.format(DATA_SOURCE_NAME_FORMAT,index);
    }

    public static List<String> generationTableNames(String tableName, Long tableSize,String format){
        if(format==null){
            format=TABLE_FORMAT;
        }
        List<String> tableNames = new ArrayList<>();
        for(int i =0;i<tableSize;i++){
            tableNames.add(tableName+String.format(format,i));
        }
        return tableNames;
    }
    public static String generationCurrentTableName(String tableName, Long tableSize,Long tableNumber,String format){
        if(format==null){
            format=TABLE_FORMAT;
        }
        return tableName+String.format(format,tableNumber);
    }

}
