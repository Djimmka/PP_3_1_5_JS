package ru.kata.spring.boot_security.demo.servise;



import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    public List<User> findAll();
    public User findById(long id);
    public User findByName(String name);
    public void save(User user);
    public void update(long id, User updUser);
    public void delete(long id);
    public void gen5Users(Role role);
}