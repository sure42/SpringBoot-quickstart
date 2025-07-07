package com.example.demo.service;

import com.example.demo.entity.QueryRequest;

import java.util.List;
import java.util.Map;

/***
 * 这里在考虑怎么能比较优雅的实现任意数据库的查询，目前有两个思路，基础JDBC / 动态连接池
 * 但这两种方法都有一个问题在于，需要显示地输入数据库密码
 * 如果不想用这两种，就只能使用SpringBoot的数据库连接池，但这种方法感觉不够灵活
 */
public interface SqlService {
    List<Map<String, Object>> query(QueryRequest request);
}
