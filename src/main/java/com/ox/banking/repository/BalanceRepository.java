package com.ox.banking.repository;

import com.ox.banking.entity.Balance;
import com.ox.banking.repository.base.BaseHistoricalRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BalanceRepository extends BaseHistoricalRepository<Balance, String> {
    Optional<Balance> findByAccountNumberAndValidToIsNull(String accountNumber);
    boolean existsByAccountNumberAndValidToIsNull(String accountNumber);
}