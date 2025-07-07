package com.example.demo.service.impl;

import com.example.demo.entity.QueryRequest;
import com.example.demo.service.SqlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SqlServiceImpl implements SqlService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //
    public List<Map<String, Object>> query(QueryRequest request) {

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        String sql = buildDynamicSql(request);
        return jdbcTemplate.queryForList(sql);
    }

    private String buildDynamicSql(QueryRequest request) {
        // 动态拼接SQL[9,10](@ref)
        StringBuilder sql = new StringBuilder("SELECT ");
        if (!request.getColumns().isEmpty() | !request.getTableName().isEmpty()) {
            sql.append(String.join(",", request.getColumns()))
                    .append(" FROM ").append(request.getTableName());
        }

        // 这里需要加一个预编译的操作，避免注入
        if (!request.getValues().isEmpty()) {
            sql.append(" WHERE ");
            List<String> clauses = new ArrayList<>();
            Iterator<String> valuesIter = request.getValues().iterator();
            Iterator<String> columnsConditionIter = request.getColumnsCondition().iterator();
            while (valuesIter.hasNext() && columnsConditionIter.hasNext()) {
                String value = valuesIter.next();
                String column = columnsConditionIter.next();
                clauses.add(column + "='" + value + "'");
            }
            log.info("SQL字符串拼接检查 | clauses={}", clauses);
            sql.append(String.join(" AND ", clauses));
        }
        return sql.toString();
    }
}
