package com.ox.banking.entity;

import com.ox.banking.entity.base.BaseHistoricalEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Balance extends BaseHistoricalEntity {
    @Id
    @Column(name = "account_number")
    private String accountNumber;
    
    private BigDecimal availableBalance;
    private BigDecimal accountingBalance;
    private BigDecimal blockedAmount;
    
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<FutureMovement> futureMovements;
}