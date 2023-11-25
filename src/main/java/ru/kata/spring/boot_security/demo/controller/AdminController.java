package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Collection;

@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String getUsers(ModelMap model) {
        Authentication context = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", context.getPrincipal());
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleService.getRoles());
        return "admin";
    }

    @PostMapping("/admin")
    public String addUser(@ModelAttribute("user") User user, @RequestParam("roles") Collection<Long> id) {
        user.setRoles(roleService.findById(id));
        userService.save(user);
        return "redirect:admin";
    }

    @PatchMapping("/admin")
    public String edit(@ModelAttribute("user") User user, @RequestParam("roles") Collection<Long> id) {
        user.setRoles(roleService.findById(id));
        userService.edit(user);
        return "redirect:admin";
    }

    @DeleteMapping("/admin")
    public String delete(@RequestParam("id") Long id) {
        userService.deleteById(id);
        return "redirect:admin";
    }
}