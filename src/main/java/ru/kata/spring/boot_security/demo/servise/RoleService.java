package ru.kata.spring.boot_security.demo.servise;

import org.springframework.security.core.GrantedAuthority;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleService {
    public Role findById(int id);
    public void save(Role role);
    public void update(int id, Role updRole);
    public void delete(int id);

    public List<Role> findAll();

    public String getAuthority(Role role);
}
