package com.malyshev2202.store.backend.repo;

import com.malyshev2202.store.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
    public User findByEmail(String email);
}
