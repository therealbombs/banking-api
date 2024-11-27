package com.ox.banking.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.ox.banking.entity.Customer;
import com.ox.banking.repository.base.BaseHistoricalRepository;

@Repository
public interface CustomerRepository extends BaseHistoricalRepository<Customer, String> {
    Optional<Customer> findByCustomerIdAndValidToIsNull(String customerId);
}