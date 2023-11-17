package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleService {
    void add(Role role);

    void delete(Long id);

    Role getRoleById(Long id);

    List<Role> getRoles();
}
