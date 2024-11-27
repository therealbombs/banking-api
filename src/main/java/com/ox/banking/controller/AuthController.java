package com.ox.banking.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ox.banking.dto.AccountDTO;
import com.ox.banking.dto.LoginRequestDTO;
import com.ox.banking.dto.LoginResponseDTO;
import com.ox.banking.entity.Account;
import com.ox.banking.entity.Customer;
import com.ox.banking.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    
    @Autowired
    private CustomerService customerService;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        log.debug("Login attempt for user: {}", request.getUsername());
        
        try {
            Customer customer = customerService.authenticate(request.getUsername(), request.getPassword());
            LoginResponseDTO response = convertToDTO(customer);
            
            log.info("Successful login for customer: {}", customer.getCustomerId());
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            log.warn("Failed login attempt for user: {}", request.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    private LoginResponseDTO convertToDTO(Customer customer) {
        LoginResponseDTO dto = new LoginResponseDTO();
        dto.setCustomerId(customer.getCustomerId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setType(customer.getType());
        dto.setPreferredLanguage(customer.getPreferredLanguage());
        dto.setLastLogin(customer.getLastLogin());
        dto.setAccounts(customer.getAccounts().stream()
            .map(this::convertToAccountDTO)
            .collect(Collectors.toList()));
        // Per ora mock del token
        dto.setToken("mock-jwt-token-" + customer.getCustomerId());
        return dto;
    }
    
    private AccountDTO convertToAccountDTO(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setAccountNumber(account.getAccountNumber());
        dto.setName(account.getName());
        dto.setType(account.getAccountType().getCode());
        dto.setPrimary(account.isPrimary());
        dto.setAttributes(account.getAccountType().getAttributes());
        return dto;
    }
}