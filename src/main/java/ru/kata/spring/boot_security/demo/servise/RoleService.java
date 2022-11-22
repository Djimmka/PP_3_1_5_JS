package ru.kata.spring.boot_security.demo.servise;

import ru.kata.spring.boot_security.demo.models.Role;

public interface RoleService {
    public Role findById(int id);
    public void save(Role role);
    public void update(int id, Role updRole);
    public void delete(int id);
}
