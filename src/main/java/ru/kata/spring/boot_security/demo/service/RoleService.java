package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.*;

@Service
@Transactional
public class RoleService {
    private final RoleDao roleDao;

    @Autowired
    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional
    public void save(Role role) {
        roleDao.save(role);
    }

    @Transactional(readOnly = true)
    public Collection<Role> findById(Long id) {
        Collection<Role> roles = null;
        if (id == 1L) {
            Role role = roleDao.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid role Id:" + id));
            roles = Collections.singleton(role);
        } else if (id == 2L) {
            Role role = roleDao.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid role Id:" + id));
            roles = Collections.singleton(role);
        }
        return roles;
    }

    @Transactional(readOnly = true)
    public List<Role> getRoles() {
        return roleDao.findAll();
    }
}