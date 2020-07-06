package com.malyshev2202.store.backend.repo;

import com.malyshev2202.store.backend.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<UserRole,Long> {
}
