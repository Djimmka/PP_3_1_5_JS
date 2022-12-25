package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.servise.RoleService;
import ru.kata.spring.boot_security.demo.servise.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class ApiRESTController {
    private final UserService userService;
    private final RoleService roleService;

    public ApiRESTController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping("/user")
    public ResponseEntity<UserForView> authenticatedUser(Principal principal) {
        User user = userService.findByName(principal.getName());
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        UserForView user1 = new UserForView(user.getId(), user.getUsername(), user.getPassword(), user.getLastName(), authorities);
        user1.setId(user.getId());
        return ResponseEntity.ok(user1);
    }

    @RequestMapping("/users")
    public ResponseEntity<List<UserForView>> usersList() {
        List<UserForView> response = new ArrayList<>();
        userService.findAll().forEach(user -> {
            Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
            UserForView user1 = new UserForView(user.getId(), user.getUsername(), user.getPassword(), user.getLastName(), authorities);
            user1.setId(user.getId());
            response.add(user1);
        });
        return ResponseEntity.ok(response);
    }

    @RequestMapping("/user/{id}")
    public ResponseEntity<UserForView> showUser(@PathVariable("id") long id) {
        User user = userService.findById(id);
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        UserForView user1 = new UserForView(user.getId(), user.getUsername(), user.getPassword(), user.getLastName(), authorities);
        user1.setId(user.getId());
        return ResponseEntity.ok(user1);
    }

    @PostMapping("/new")
    public ResponseEntity<Boolean> newUser(@ModelAttribute("user") UserForView user) {
        try {
            User userWithRoles = new User(user.password, user.username, user.lastName);
            user.authorities.forEach(from -> {
                roleService.findAll().forEach(role -> {
                    if (role.getAuthority().equals(from.getAuthority())) {
                        userWithRoles.setRole(role);
                    }
                });
            });
            userService.save(userWithRoles);
            return ResponseEntity.ok(true);
        } catch (RuntimeException e) {
            return ResponseEntity.ok(false);
        }
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<Boolean> editUser(@PathVariable("id") long id, @ModelAttribute("user") UserForView user) {
        try {
            User userWithRoles = new User(user.password, user.username, user.lastName);
            userWithRoles.setId(user.getId());
            user.authorities.forEach(from -> {
                roleService.findAll().forEach(role -> {
                    if (role.getAuthority().equals(from.getAuthority())) {
                        userWithRoles.setRole(role);
                    }
                });
            });
            userService.save(userWithRoles);
            return ResponseEntity.ok(true);
        } catch (RuntimeException e) {
            return ResponseEntity.ok(false);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") long id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok(true);
        } catch (RuntimeException e) {
            return ResponseEntity.ok(false);
        }
    }

    private class UserForView {
        private long id;
        private String username;
        private String password;
        private String lastName;
        private Collection<? extends GrantedAuthority> authorities;

        public UserForView(long id, String username, String password, String lastName, Collection<? extends GrantedAuthority> authorities) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.lastName = lastName;
            this.authorities = authorities;
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

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = authorities;
        }
    }
}
