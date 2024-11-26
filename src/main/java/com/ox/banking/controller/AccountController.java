package com.ox.banking.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ox.banking.dto.BalanceDTO;
import com.ox.banking.dto.TransactionDTO;
import com.ox.banking.service.AccountService;

import lombok.extern.slf4j.Slf4j;

@Slf4j 
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    
	
    @Autowired
    private AccountService accountService;
    
    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<BalanceDTO> getBalance(@PathVariable String accountNumber) {
    	log.debug("Retrieving balance for account: {}", ""+accountNumber);
    	
        return ResponseEntity.ok(accountService.getBalance(accountNumber));
    }
    
    @GetMapping("/{accountNumber}/transactions")
    public ResponseEntity<List<TransactionDTO>> getTransactions(
            @PathVariable String accountNumber,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(
            accountService.getTransactions(accountNumber, fromDate, toDate, page, size)
        );
    }
}
