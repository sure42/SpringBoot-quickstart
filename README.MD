## 1. 数据集初始化

```SQL
CREATE DATABASE IF NOT EXISTS test;
USE test;

DROP TABLE IF EXISTS user;
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by VARCHAR(255) COMMENT '创建者标识，记录数据创建人',
    update_by VARCHAR(255) COMMENT '更新者标识，记录最后修改人'
);
```

## 测试数据

```json
[
  {
    "username": "zhangs",
    "password": "123456",
    "email": "zhangs@123.com"
  },
  {
    "username": "liwen",
    "password": "qwerty",
    "email": "liwen@163.com"
  },
  {
    "username": "wanghao",
    "password": "pass123",
    "email": "wanghao@qq.com"
  },
  {
    "username": "zhaoyi",
    "password": "abc789",
    "email": "zhaoyi@outlook.com"
  },
  {
    "username": "sunyang",
    "password": "sun2024",
    "email": "sunyang@gmail.com"
  },
  {
    "username": "qianlei",
    "password": "q1w2e3",
    "email": "qianlei@yahoo.com"
  },
  {
    "username": "zhoumin",
    "password": "zxcvbn",
    "email": "zhoumin@hotmail.com"
  },
  {
    "username": "wuxin",
    "password": "wu!987",
    "email": "wuxin@126.com"
  },
  {
    "username": "zhenglong",
    "password": "gl2025",
    "email": "zhenglong@icloud.com"
  },
  {
    "username": "fengjie",
    "password": "fj#456",
    "email": "fengjie@proton.me"
  },
  {
    "username": "chenlu",
    "password": "cl!789",
    "email": "chenlu@live.com"
  },
  {
    "username": "huyan",
    "password": "hy_123",
    "email": "huyan@yandex.com"
  },
  {
    "username": "linfei",
    "password": "lf!666",
    "email": "linfei@mail.com"
  },
  {
    "username": "xuweilai",
    "password": "xwl2025",
    "email": "xuweilai@aol.com"
  },
  {
    "username": "mengqi",
    "password": "mq@888",
    "email": "mengqi@zoho.com"
  },
  {
    "username": "guojing",
    "password": "gj*999",
    "email": "guojing@tutanota.com"
  },
  {
    "username": "pangbo",
    "password": "pb1234",
    "email": "pangbo@fastmail.com"
  },
  {
    "username": "xiekai",
    "password": "xk!777",
    "email": "xiekai@disroot.org"
  },
  {
    "username": "hemin",
    "password": "hm_2025",
    "email": "hemin@riseup.net"
  },
  {
    "username": "tanglin",
    "password": "tl#555",
    "email": "tanglin@runbox.com"
  }
]
```