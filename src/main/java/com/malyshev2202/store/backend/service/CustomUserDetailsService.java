package com.malyshev2202.store.backend.service;

import com.malyshev2202.store.backend.model.User;
import com.malyshev2202.store.backend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo dao;
    // переопределение метода который заполняет UserDetails данными пользователя
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User myUser= dao.findByEmail(email);
        if (myUser == null) {
            throw new UsernameNotFoundException("Unknown user: "+email);
        }
        UserDetails user = org.springframework.security.core.userdetails.User.builder()
                .username(myUser.getEmail())
                .password(myUser.getPassword())
                .roles(myUser.getRoles().toString())
                .build();
        return user;
    }
    //метод по получению авторизованного пользователя по имени

    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
    //поиск пользователя по имени
    public User getCurrentUser() {
        return dao.findByEmail(getCurrentUsername());


    }
}
