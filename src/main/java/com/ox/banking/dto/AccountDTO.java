package com.ox.banking.dto;

import lombok.Data;
import java.util.Map;

@Data
public class AccountDTO {
    private String accountNumber;
    private String name;
    private String type;
    private boolean primary;
    private Map<String, String> attributes;
}