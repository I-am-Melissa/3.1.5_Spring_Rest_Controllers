//package ru.kata.spring.boot_security.demo;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import ru.kata.spring.boot_security.demo.models.Role;
//import ru.kata.spring.boot_security.demo.models.User;
//import ru.kata.spring.boot_security.demo.service.RoleService;
//import ru.kata.spring.boot_security.demo.service.UserService;
//
//import javax.annotation.PostConstruct;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.HashSet;
//
//@RequiredArgsConstructor
//@Component
//public class DbFill {
//
//    private final UserService userService;
//    private final RoleService roleService;
//
//
//    @PostConstruct
//    private void postConstruct() {
//        Role user = new Role("ROLE_USER");
//        Role admin = new Role("ROLE_ADMIN");
//        roleService.add(user);
//        roleService.add(admin);
//        userService.add(new User("user@gmail.com", "user", new HashSet<>(Collections.singleton(user))));
//        userService.add(new User("admin@gmail.com", "admin", new HashSet<>(Arrays.asList(user, admin))));
//
//    }
//}
