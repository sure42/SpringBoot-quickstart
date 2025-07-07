package com.example.demo.service;

import com.example.demo.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {
    User saveUser(User user);

    User getUserById(Long id);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    Page<User> getUserPage(Integer page, Integer size);

}
