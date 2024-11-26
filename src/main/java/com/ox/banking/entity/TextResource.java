package com.ox.banking.entity;

import com.ox.banking.entity.base.BaseHistoricalEntity;
import com.ox.banking.enums.Language;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"text_key", "language", "version"})
})
public class TextResource extends BaseHistoricalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text_key")
    private String key;

    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(columnDefinition = "TEXT")
    private String text;

    private Integer version;
}