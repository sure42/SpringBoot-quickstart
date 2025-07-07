package com.example.demo.entity;

import lombok.Data;

import java.util.List;

@Data
public class QueryRequest {
    // 用于切换数据源
    private String dataSourceId;
    private String tableName;
    // 要查询的列
    private List<String> columns;
    // 对应的值
    // 这里先限制到单值查询 + 等于
    private List<String> values;
    private List<String> columnsCondition;

    // 用于其他数据库连接模式
    private String url;
    private String userName;
    private String password;

}
