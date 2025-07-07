package com.example.demo.service.impl;


import com.example.demo.entity.QueryRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class SqlServiceImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private SqlServiceImpl sqlService;

    @Captor
    private ArgumentCaptor<Object[]> paramsCaptor;

    /**
     * 加载所有SQL注入测试用例
     */
    static List<SqlInjectionTestCase> loadInjectionTestCases() throws Exception {
        return loadJsonTestCases("/test-data/sql-injection-tests.json",
                new TypeReference<List<SqlInjectionTestCase>>() {
                });
    }

    /**
     * 加载单个正常请求测试用例
     */
    static QueryRequest loadValidRequest() throws Exception {
        return loadJsonTestCases("/test-data/valid-request.json",
                new TypeReference<QueryRequest>() {
                });
    }

    /**
     * 通用JSON加载方法
     */
    private static <T> T loadJsonTestCases(String resourcePath, TypeReference<T> typeRef)
            throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = SqlServiceImplTest.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IllegalArgumentException("Resource not found: " + resourcePath);
            }
            return mapper.readValue(is, typeRef);
        }
    }

    @BeforeEach
    void setUp() {
        // 所有测试模拟返回空结果集
        lenient().when(jdbcTemplate.queryForList(any(String.class), any(Object[].class)))
                .thenReturn(Collections.emptyList());
    }

    @Test
    void testSqlGenerationWithSingleCondition() {
        QueryRequest request = new QueryRequest();
        request.setTableName("tb_users");
        request.setColumns(List.of("*"));
        request.setColumnsCondition(List.of("username"));
        request.setValues(List.of("john.doe"));

        // 构建SQL
        String preparedSql = sqlService.buildDynamicSql(request);

        // 验证
        assertEquals("SELECT * FROM tb_users WHERE username = ?", preparedSql);
        assertEquals(1, request.getValues().size());
        assertEquals("john.doe", request.getValues().get(0));
    }

    // 嵌套类：用于解析测试用例JSON
    @Data
    static class SqlInjectionTestCase {
        private String name;
        private QueryRequest request;
        private String expectedSql;
        private List<Object> expectedParams;

    }
}
