package com.ox.banking.dto;

import com.ox.banking.enums.CustomerType;
import com.ox.banking.enums.Language;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class LoginResponseDTO {
    private String customerId;
    private String firstName;
    private String lastName;
    private CustomerType type;
    private Language preferredLanguage;
    private LocalDateTime lastLogin;
    private List<AccountDTO> accounts;
    private String token;
}