package com.example.SimpleTestServer.controller;

import com.example.SimpleTestServer.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final List<User> users = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public ApiController() {
        users.add(new User(counter.incrementAndGet(), "Иван Иванов", "ivan@example.com"));
        users.add(new User(counter.incrementAndGet(), "Мария Петрова", "maria@example.com"));
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return users;
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        User newUser = new User(counter.incrementAndGet(), user.getName(), user.getEmail());
        users.add(newUser);
        return newUser;
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = getUserById(id);
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        return user;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        users.removeIf(user -> user.getId().equals(id));
    }

    @GetMapping("/hello")
    public String hello() {
        return "Привет! Сервер работает!";
    }
}
