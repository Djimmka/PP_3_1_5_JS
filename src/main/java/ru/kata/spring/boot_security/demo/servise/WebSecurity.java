package ru.kata.spring.boot_security.demo.servise;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
@Component
public class WebSecurity {
    public boolean checkUserId(Authentication authentication, int id) {
        Collection<? extends GrantedAuthority> autorities = authentication.getAuthorities();
        if (autorities.contains(new SimpleGrantedAuthority(String.valueOf(id)))) {
            return true;
        }
        return false;
    }
}