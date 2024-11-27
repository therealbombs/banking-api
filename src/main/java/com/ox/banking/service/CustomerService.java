package com.ox.banking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.ox.banking.entity.Customer;
import com.ox.banking.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    public Customer authenticate(String username, String password) {
        // Per ora mock, poi integreremo con Keycloak
        return customerRepository.findByCustomerIdAndValidToIsNull(username)
            .orElseThrow(() -> new AuthenticationException("Invalid credentials") {});
    }
}