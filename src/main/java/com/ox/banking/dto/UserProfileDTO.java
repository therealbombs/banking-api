package com.ox.banking.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ox.banking.entity.Product;
import com.ox.banking.enums.Language;
import com.ox.banking.enums.UserProfileType;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserProfileDTO {
    private String userId;
    private UserProfileType profileType; // cambiato da profile
    private Language language;
    private String contractType;
    private boolean active;
    private List<Product> products;
    private LocalDateTime lastLogin;
    private String preferredName;
    private Integer textVersion;
}