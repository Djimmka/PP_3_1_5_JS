package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.servise.RoleService;
import ru.kata.spring.boot_security.demo.servise.UserService;

import javax.validation.Valid;
import java.beans.Encoder;
import java.security.Principal;


@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String index(Model model) {
        //userService.gen5Users();
        model.addAttribute("users", userService.findAll());
        return "admin/index";
    }

    @GetMapping("/admin/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "admin/show";
    }

    @GetMapping("/admin/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "admin/new";
    }

    @GetMapping("/admin/gen5")
    public String gen5(Model model) {
        Role role = new Role();
        role.setRole("ROLE_USER");
        roleService.update(1,role);
        userService.gen5Users(role);
        return "admin/index";
    }

    @GetMapping("/admin/gen5mod")
    public String gen5mod(Model model) {
        Role role = new Role();
        role.setRole("USER");
        roleService.update(1,role);
        userService.gen5Users(role);
        return "admin/index";
    }

    @PostMapping("/admin")
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingRequest) {
        if (bindingRequest.hasErrors()) return "admin/new";
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRole(roleService.findById(1));
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.findById(id));
        return "admin/edit";
    }


    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingRequest,
                         @PathVariable("id") long id) {
        if (bindingRequest.hasErrors()) return "admin/edit";
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userService.update(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping ("/user")
    public String myPage (Principal principal, Model model) {
        model.addAttribute("user", userService.findByName(principal.getName()));
        model.addAttribute("simpleGrantedAuthority", new SimpleGrantedAuthority("ADMIN"));
        return "user";
    }

}
