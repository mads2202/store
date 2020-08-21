package com.malyshev2202.store.backend.repo.cassandraRepo;

import com.malyshev2202.store.backend.model.BasketItem;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BasketItemDAO extends CassandraRepository<BasketItem,Long> {
    public List<BasketItem> findAllByBasketId(Long id);
    public List<BasketItem> findAllByProductId(Long id);


}

