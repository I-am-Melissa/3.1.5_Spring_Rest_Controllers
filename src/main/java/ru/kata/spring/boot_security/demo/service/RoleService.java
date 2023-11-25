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
    public Collection<Role> findById(Collection<Long> id) {
        Collection<Role> roles = new ArrayList<>();
        for (Long roleId : id) {
            Role role = roleDao.findById(roleId).orElseThrow(() -> new IllegalArgumentException("Invalid role Id:" + id));
            roles.add(role);
        }
        return roles;
    }

    @Transactional(readOnly = true)
    public List<Role> getRoles() {
        return roleDao.findAll();
    }
}