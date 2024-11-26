package com.ox.banking.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.ox.banking.entity.base.BaseHistoricalEntity;
import com.ox.banking.enums.Language;
import com.ox.banking.enums.UserProfileType;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
public class UserProfile extends BaseHistoricalEntity {
	@Id
	private String userId;

	@Enumerated(EnumType.STRING)
	private UserProfileType profileType; // cambiato da profile

	@Enumerated(EnumType.STRING)
	private Language language;

	private String contractType;
	private boolean active;

	@ElementCollection
	private List<Product> products;

	private LocalDateTime lastLogin;
	private String preferredName;
	private Integer textVersion;
}