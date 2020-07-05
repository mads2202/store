package com.malyshev2202.store.repo;

import com.malyshev2202.store.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepo extends JpaRepository<Client,Long> {
    public Client findByEmail(String email);
}
