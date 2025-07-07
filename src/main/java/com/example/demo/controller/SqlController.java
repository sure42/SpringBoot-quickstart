package com.example.demo.controller;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.demo.entity.QueryRequest;
import com.example.demo.service.SqlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.example.demo.utils.response.createResponse;
import static com.example.demo.utils.response.isValidParam;

@RestController
@RequestMapping("/sql")
@Slf4j
public class SqlController {

    @Autowired
    private SqlService sqlService;

    @DS("data1")
    @PostMapping("/query")
    public ResponseEntity<Map<String, Object>> query(@RequestBody QueryRequest request) {

        // 这里实际上还少对于列和字段的校验----toString做了一下
        if (!isValidParam(request.getTableName()) |
                !isValidParam(request.getColumns().toString()) |
                !isValidParam(request.getValues().toString())) {
            throw new SecurityException("非法表名或参数");
        }
        if (!(request.getColumnsCondition().size() == request.getValues().size())) {
            throw new SecurityException("非法参数");
        }
        return createResponse("success", "query", sqlService.query(request));
    }
}
