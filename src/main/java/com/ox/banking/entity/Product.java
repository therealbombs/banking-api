package com.ox.banking.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Product {
	private String name;
	private String status;
}