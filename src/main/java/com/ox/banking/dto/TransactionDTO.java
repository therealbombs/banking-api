package com.ox.banking.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private Long id;
    private BigDecimal amount;
    private LocalDateTime dateTime;
    private String description;
}