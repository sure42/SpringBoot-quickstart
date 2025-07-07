package com.example.demo.service.impl;

import com.example.demo.entity.QueryRequest;
import com.example.demo.service.SqlService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SqlServiceImpl implements SqlService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> query(QueryRequest request) {

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        String sql = buildDynamicSql(request);
        log.debug("SQL查询语句 | sql={}", sql);
        log.debug("SQL参数数量 | count={}", request.getValues().size());
        return jdbcTemplate.queryForList(
                sql,
                request.getValues().toArray()
        );
    }

    String buildDynamicSql(QueryRequest request) {
        // 动态拼接SQL[9,10](@ref)
        StringBuilder sql = new StringBuilder("SELECT ");
        if (!request.getColumns().isEmpty() | !request.getTableName().isEmpty()) {
            sql.append(String.join(",", request.getColumns()))
                    .append(" FROM ").append(request.getTableName());
        }

        // 采用占位符的方式，避免SQL注入
        if (!request.getValues().isEmpty() && !request.getColumnsCondition().isEmpty()) {
            sql.append(" WHERE ");
            List<String> clauses = new ArrayList<>();

            Iterator<String> columnsConditionIter = request.getColumnsCondition().iterator();

            while (columnsConditionIter.hasNext()) {
                String column = columnsConditionIter.next();
                clauses.add(column + " = ?");
            }

            sql.append(String.join(" AND ", clauses));
        }
        return sql.toString();
    }
}
