package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class RedisConnectionTest {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void testSetAndGet() {
        redisTemplate.opsForValue().set("test:key", "hello-docker-redis");
        String value = redisTemplate.opsForValue().get("test:key");
        assert "hello-docker-redis".equals(value);
    }
}