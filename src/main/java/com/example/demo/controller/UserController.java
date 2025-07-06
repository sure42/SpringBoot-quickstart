package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.example.demo.utils.response.createResponse;

@RestController
@RequestMapping("/users")
@Slf4j
// 激活审计功能，自动记录创建和更新时间
@EnableJpaAuditing
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/test")
    public ResponseEntity<Map<String, Object>> test() {
        System.out.println("基本接口测试");
        return createResponse("success", "test", "test");
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return createResponse("success", "User created", savedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return createResponse("error", "User not found", null);
        }
        return createResponse("success", "User found", user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return createResponse("success", "User updated", updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return createResponse("success", "User deleted", null);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getUsersPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<User> users = userService.getUserPage(page, size);
        return createResponse("success", "Users page", users);
    }
}