package com.example.demo.repository;

import com.example.demo.entity.CustomerDetailsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDetailsRepository
        extends CrudRepository<CustomerDetailsEntity, Long> {
}

