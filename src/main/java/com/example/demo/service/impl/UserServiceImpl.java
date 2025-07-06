package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@EnableCaching
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    @Cacheable(value = "users", key = "#id")
    @Transactional
    public User saveUser(User user) {
        log.info("保存数据: {}", user.getUsername());
        return userRepository.save(user);
    }

    @Override
    @Cacheable(value = "users", key = "#id")
    public User getUserById(Long id) {
        log.info("读取数据: {}", id);
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    @CachePut(value = "users", key = "#user.id")
    public User updateUser(Long id, User user) {
        log.info("更新数据: {}", id);
        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    @CacheEvict(value = "user", key = "#id")
    public void deleteUser(Long id) {
        log.info("删除数据: {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public Page<User> getUserPage(Integer page, Integer size) {
        return userRepository.findAll(PageRequest.of(page - 1, size));
    }
}

