package com.malyshev2202.store.backend.repo.cassandraRepo;

import com.malyshev2202.store.backend.model.CategoryAndProduct;
import org.springframework.data.cassandra.repository.CassandraRepository;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CategoryAndProductDAO extends CassandraRepository<CategoryAndProduct, Long> {
    List<CategoryAndProduct> findAllByCategoryName(String name);
    List<CategoryAndProduct> findByProductId(Long id);



}
