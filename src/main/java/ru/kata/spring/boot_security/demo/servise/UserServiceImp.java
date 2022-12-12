package ru.kata.spring.boot_security.demo.servise;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImp implements UserService {

    private final UsersRepository usersRepository;

    @Autowired
    public UserServiceImp(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<User> findAll() {
        return usersRepository.findAll();
    }

    public User findById(long id) {
        Optional<User> user = usersRepository.findById(id);
        return user.orElse(null);
    }

    public User findByName(String name) {
        Optional<User> user = usersRepository.findAll().stream().filter(s -> Objects.equals(s.getUsername(), name)).findFirst();
        return user.orElse(null);
    }

    @Transactional
    public void save(User user) {
        usersRepository.save(user);
    }

    @Transactional
    public void update(long id, User updUser) {
        updUser.setId(id);
        usersRepository.save(updUser);
    }

    @Transactional
    public void delete(long id) {
        usersRepository.deleteById(id);
    }

    @Transactional
    public void gen5Users(Role role) {
        for (int i = 1; i < 6; i++) {
            User user = new User(String.valueOf(2220 + i), "name_" + i, "lastName_" + i, role);
            update((long) i, user);
        }
    }

}
