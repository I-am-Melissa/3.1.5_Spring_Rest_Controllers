package ru.kata.spring.boot_security.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {

    public static void main(String[] args) {

//		SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
        ApplicationContext context = SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
        UserService userService = context.getBean(UserService.class);
        RoleService roleService = context.getBean(RoleService.class);

        Role admin = new Role("ROLE_ADMIN");
        Role user = new Role("ROLE_USER");

        roleService.save(user);
        roleService.save(admin);

        userService.save(new User("Ivan", "Ivanov", 20, "user", "user", new HashSet<>(Collections.singleton(user))));
        userService.save(new User("Petr", "Petrov", 20, "admin", "admin", new HashSet<>(Arrays.asList(user, admin))));
    }
}