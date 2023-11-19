package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin/users")
    public String getUsers(ModelMap model) {
        model.addAttribute("users", userService.getUsers());
        return "users";
    }

    @GetMapping("/admin/add")
    public String addUser(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getRoles());
        return "add";
    }

    @PostMapping("/admin/add")
    public String addUser(@ModelAttribute("user") User user, @RequestParam("roles") Long id) {
        user.setRoles(roleService.findById(id));
        userService.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/edit")
    public String edit(@RequestParam("id") Long id, ModelMap model) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("roles", roleService.getRoles());
        return "edit";
    }

    @PatchMapping("/admin/edit")
    public String edit(@ModelAttribute("user") User user, @RequestParam("roles") Long id) {
        user.setRoles(roleService.findById(id));
        userService.edit(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/admin/delete")
    public String delete(@RequestParam("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }
}