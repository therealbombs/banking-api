package com.ox.banking.entity.base;

import java.time.LocalDateTime;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class BaseHistoricalEntity {
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;
    private Boolean active;

    @PrePersist
    protected void onCreate() {
        validFrom = LocalDateTime.now();
        lastModifiedDate = LocalDateTime.now();
        active = true;
    }

    @PreUpdate
    protected void onUpdate() {
        lastModifiedDate = LocalDateTime.now();
    }
}