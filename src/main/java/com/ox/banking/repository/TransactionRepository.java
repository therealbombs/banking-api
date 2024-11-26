package com.ox.banking.repository;

import com.ox.banking.entity.Transaction;
import com.ox.banking.repository.base.BaseHistoricalRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends BaseHistoricalRepository<Transaction, Long> {
    List<Transaction> findByAccountNumberAndDateTimeBetweenAndValidToIsNull(
        String accountNumber,
        LocalDateTime dateTimeFrom,
        LocalDateTime dateTimeTo,
        Pageable pageable
    );
}