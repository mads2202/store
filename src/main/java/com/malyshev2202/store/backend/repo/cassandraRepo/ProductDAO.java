package com.malyshev2202.store.backend.repo.cassandraRepo;

import com.malyshev2202.store.backend.model.Product;
import org.springframework.data.cassandra.repository.CassandraRepository;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductDAO extends CassandraRepository<Product,Long> {
    //поиск товара по имени

    public List<Product> findAllByNameContaining( String name);


}