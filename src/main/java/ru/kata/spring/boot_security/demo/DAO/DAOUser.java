package ru.kata.spring.boot_security.demo.DAO;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface DAOUser {
    public List<User> findAll();
    public User findByName(String name);
    public User findById(long id);
    public void save(User user);
    public void delete(long id);
}
