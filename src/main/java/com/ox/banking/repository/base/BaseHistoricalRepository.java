package com.ox.banking.repository.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseHistoricalRepository<T, ID> extends JpaRepository<T, ID> {
    Optional<T> findByValidToIsNull();
    List<T> findAllByValidToIsNull();
}