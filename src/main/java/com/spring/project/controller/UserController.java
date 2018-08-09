package com.spring.project.controller;


import com.spring.project.entity.Article;
import com.spring.project.model.UserBindingModel;
import com.spring.project.entity.User;
import com.spring.project.repository.RoleRepository;
import com.spring.project.repository.UserRepository;
import com.spring.project.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class UserController {

    private final
    RoleRepository roleRepository;

    private final
    UserRepository userRepository;

    private final UserService userService;

    public UserController(UserService userService, UserRepository userRepository, RoleRepository roleRepository, UserRepository userRepository1) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository1;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("view", "user/register");

        return "base-layout";
    }

    @PostMapping("/register")
    public String registerProcess(UserBindingModel userBindingModel) {
        if (!userService.register(userBindingModel)) {
            return "redirect:/register";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("view", "user/login");

        return "base-layout";
    }

    @PostMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profilePage(Model model) {
        User user = userService.getProfilePage();
        List<Article> articles =userService.getArticles();

        model.addAttribute("articles",articles);
        model.addAttribute("user", user);
        model.addAttribute("view", "user/profile");
        return "base-layout";
    }

    public RoleRepository getRoleRepository() {
        return roleRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

}

