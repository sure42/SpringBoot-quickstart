package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 类似于 MyBatis 中的 Mqpper类，可能更像 Mybatis Plus，但相对而言，更加方便一些，不需要再编写具体的查找逻辑
 * 可以自动根据函数名推断对应的功能
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

