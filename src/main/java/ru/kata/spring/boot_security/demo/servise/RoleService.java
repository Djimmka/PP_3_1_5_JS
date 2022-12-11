package ru.kata.spring.boot_security.demo.servise;

import org.springframework.security.core.GrantedAuthority;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleService {
    public Role findById(long id);
    public void save(Role role);
    public void update(long id, Role updRole);
    public void delete(long id);

    public List<Role> findAll();

    public String getAuthority(Role role);
}
