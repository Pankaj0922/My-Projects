package com.bookstore.controller;

import com.bookstore.entity.User;
import com.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login") // Add this line
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        Optional<User> user = userService.authenticateUser(username, password);

        if (user.isPresent()) {
            // Successful login, you can redirect to the home page or perform other actions
            return "redirect:/";
        } else {
            // Failed login, display an error message or redirect to a login page
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }
}
