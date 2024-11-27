package com.ox.banking.repository;

import com.ox.banking.entity.AccountType;
import com.ox.banking.repository.base.BaseHistoricalRepository;

import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AccountTypeRepository extends BaseHistoricalRepository<AccountType, Long> {
    Optional<AccountType> findByCodeAndValidToIsNull(String code);
}