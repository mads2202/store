package com.malyshev2202.store.backend.repo.cassandraRepo;

import com.malyshev2202.store.backend.model.User;
import org.springframework.data.cassandra.repository.CassandraRepository;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface UserDao extends CassandraRepository<User,String> {
    public User findByEmail(String email);


}
