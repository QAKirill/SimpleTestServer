package com.example.SimpleTestServer.controller;

import com.example.SimpleTestServer.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UiController {

    private final ApiController apiController;

    public UiController(ApiController apiController) {
        this.apiController = apiController;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        List<User> users = apiController.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("newUser", new User());
        return "index";
    }

    @PostMapping("/add-user")
    public String addUser(@ModelAttribute User newUser) {
        apiController.createUser(newUser);
        return "redirect:/"; // Перенаправляем на главную
    }
}