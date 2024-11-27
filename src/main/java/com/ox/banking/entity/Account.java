package com.ox.banking.entity;

import com.ox.banking.entity.base.BaseHistoricalEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "accounts")
public class Account extends BaseHistoricalEntity {
    @Id
    private String accountNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_type_id")
    private AccountType accountType;
    
    private String name;
    private boolean primary;
    private boolean active;
}