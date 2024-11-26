package com.ox.banking.entity;

import com.ox.banking.entity.base.BaseHistoricalEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Transaction extends BaseHistoricalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String accountNumber;
    private BigDecimal amount;
    
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    
    private String description;
}
