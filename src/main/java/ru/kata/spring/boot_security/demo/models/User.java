package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;


@Entity
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String lastName;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)//(cascade = CascadeType.ALL)
    private Set<Role> role = new HashSet<>();

    public User() {
    }

    public User(String username, String lastName) {
        this.username = username;
        this.lastName = lastName;
    }

    public User(String password, String username, String lastName) {
        this(username, lastName);
        this.password = password;
    }

    public User(String password, String name, String lastName, Role role) {
        this(password, name, lastName);
        this.setRole(role);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role.add(role);
    }

    public void deleteRole(Role role) {
        this.role.remove(role);
    }
}
