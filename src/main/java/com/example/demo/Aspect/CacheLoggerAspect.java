package com.example.demo.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class CacheLoggerAspect {
    @Pointcut("@annotation(org.springframework.cache.annotation.Cacheable)")
    public void cacheableMethods() {}

    @AfterReturning("cacheableMethods()")
    public void logCacheHit(Cacheable cacheable, Object result) {
        log.debug("【缓存命中】{} | key={}", cacheable.value());
    }
}