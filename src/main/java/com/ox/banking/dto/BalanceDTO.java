package com.ox.banking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class BalanceDTO {
	private BigDecimal availableBalance;
	private BigDecimal accountingBalance;
	private LocalDateTime lastUpdate;
	private BigDecimal blockedAmount;
	private List<FutureMovement> futureMovements;

	@Data
	public static class FutureMovement {
		private LocalDateTime date;
		private BigDecimal amount;
		private String description;
	}
}