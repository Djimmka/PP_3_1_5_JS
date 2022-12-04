package ru.kata.spring.boot_security.demo.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

//@Repository
public class DAOUserImp implements DAOUser {
//    @PersistenceContext
//    private EntityManager entityManager;

//    @Autowired
//    public DAOUserImp(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }


    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findByName(String name) {
        return null;
    }

    @Override
    public User findById(long id) {
        return null;
    }

    @Override
    public void save(User user) {

    }

    @Override
    public void delete(long id) {

    }
}
