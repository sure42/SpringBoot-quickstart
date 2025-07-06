package com.example.demo.service;

import com.example.demo.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {
    public User saveUser(User user);
    public User getUserById(Long id);
    public User updateUser(Long id, User user);
    public void deleteUser(Long id);
    public Page<User> getUserPage(Integer page, Integer size);

}
