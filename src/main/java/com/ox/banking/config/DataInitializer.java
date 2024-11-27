package com.ox.banking.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.ox.banking.entity.Account;
import com.ox.banking.entity.AccountType;
import com.ox.banking.entity.Customer;
import com.ox.banking.enums.CustomerType;
import com.ox.banking.enums.Language;
import com.ox.banking.repository.AccountRepository;
import com.ox.banking.repository.AccountTypeRepository;
import com.ox.banking.repository.CustomerRepository;

@Component
@Profile("dev")
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private AccountTypeRepository accountTypeRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Override
    public void run(String... args) {
        createAccountTypes();
        createCustomers();
    }
    
    private void createAccountTypes() {
        AccountType currentAccount = new AccountType();
        currentAccount.setCode("CA");
        currentAccount.setName("Conto Corrente");
        currentAccount.setDescription("Conto per operazioni quotidiane");
        currentAccount.setAttributes(Map.of(
            "hasDebitCard", "true",
            "hasCreditCard", "true",
            "hasOverdraft", "true"
        ));
        accountTypeRepository.save(currentAccount);
        
        AccountType savingsAccount = new AccountType();
        savingsAccount.setCode("SA");
        savingsAccount.setName("Conto Deposito");
        savingsAccount.setDescription("Conto per risparmi");
        savingsAccount.setAttributes(Map.of(
            "interestRate", "2.5",
            "minimumBalance", "1000",
            "withdrawalNotice", "33"
        ));
        accountTypeRepository.save(savingsAccount);
    }
    
    private void createCustomers() {
        Customer customer = new Customer();
        customer.setCustomerId("CUST001");
        customer.setFirstName("Mario");
        customer.setLastName("Rossi");
        customer.setType(CustomerType.PREMIUM);
        customer.setPreferredLanguage(Language.IT);
        customer.setValidTo(null);
        customerRepository.save(customer);
        
        Account account1 = new Account();
        account1.setAccountNumber("IT001122334455");
        account1.setCustomer(customer);
        account1.setAccountType(accountTypeRepository.findByCodeAndValidToIsNull("CA").get());
        account1.setName("Conto Principale");
        account1.setPrimary(true);
        accountRepository.save(account1);
        
        Account account2 = new Account();
        account2.setAccountNumber("IT998877665544");
        account2.setCustomer(customer);
        account2.setAccountType(accountTypeRepository.findByCodeAndValidToIsNull("SA").get());
        account2.setName("Risparmi");
        account2.setPrimary(false);
        accountRepository.save(account2);
    }
}