package com.ox.banking.entity;

import com.ox.banking.entity.base.BaseHistoricalEntity;
import com.ox.banking.enums.CustomerType;
import com.ox.banking.enums.Language;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "customers")
public class Customer extends BaseHistoricalEntity {
    @Id
    private String customerId;
    
    private String firstName;
    private String lastName;
    private LocalDateTime lastLogin;
    
    @Enumerated(EnumType.STRING)
    private CustomerType type;
    
    @Enumerated(EnumType.STRING)
    private Language preferredLanguage;
    
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Account> accounts;
}