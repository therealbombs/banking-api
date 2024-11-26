
package com.ox.banking.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ox.banking.dto.BalanceDTO;
import com.ox.banking.dto.TransactionDTO;
import com.ox.banking.entity.Balance;
import com.ox.banking.entity.FutureMovement;
import com.ox.banking.entity.Transaction;
import com.ox.banking.exception.ResourceNotFoundException;
import com.ox.banking.repository.AccountRepository;
import com.ox.banking.repository.BalanceRepository;
import com.ox.banking.repository.TransactionRepository;

@Service
@Transactional(readOnly = true)
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private BalanceRepository balanceRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	public BalanceDTO getBalance(String accountNumber) {
		Balance balance = balanceRepository.findByAccountNumberAndValidToIsNull(accountNumber)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Account %s not found", accountNumber)));

		return convertToBalanceDTO(balance);
	}

	public List<TransactionDTO> getTransactions(String accountNumber, LocalDate fromDate, LocalDate toDate, int page,
			int size) {

		// Verifica prima che l'account esista
		if (!balanceRepository.existsByAccountNumberAndValidToIsNull(accountNumber)) {
			throw new ResourceNotFoundException(String.format("Account %s not found", accountNumber));
		}

		LocalDateTime fromDateTime = fromDate != null ? fromDate.atStartOfDay()
				: LocalDate.now().minusMonths(1).atStartOfDay();

		LocalDateTime toDateTime = toDate != null ? toDate.atTime(23, 59, 59) : LocalDate.now().atTime(23, 59, 59);

		return transactionRepository
				.findByAccountNumberAndDateTimeBetweenAndValidToIsNull(accountNumber, fromDateTime, toDateTime,
						PageRequest.of(page, size))
				.stream().map(this::convertToTransactionDTO).collect(Collectors.toList());
	}

	private BalanceDTO convertToBalanceDTO(Balance balance) {
		BalanceDTO dto = new BalanceDTO();
		dto.setAvailableBalance(balance.getAvailableBalance());
		dto.setAccountingBalance(balance.getAccountingBalance());
		dto.setLastUpdate(balance.getLastUpdate());
		dto.setBlockedAmount(balance.getBlockedAmount());

		if (balance.getFutureMovements() != null) {
			dto.setFutureMovements(balance.getFutureMovements().stream().map(this::convertToFutureMovementDTO)
					.collect(Collectors.toList()));
		}

		return dto;
	}

	private TransactionDTO convertToTransactionDTO(Transaction transaction) {
		TransactionDTO dto = new TransactionDTO();
		dto.setId(transaction.getId());
		dto.setAmount(transaction.getAmount());
		dto.setDateTime(transaction.getDateTime());
		dto.setDescription(transaction.getDescription());
		return dto;
	}

	private BalanceDTO.FutureMovement convertToFutureMovementDTO(FutureMovement movement) {
		BalanceDTO.FutureMovement dto = new BalanceDTO.FutureMovement();
		dto.setDate(movement.getDate());
		dto.setAmount(movement.getAmount());
		dto.setDescription(movement.getDescription());
		return dto;
	}

	@Transactional
	public void updateBalance(String accountNumber, BalanceDTO balanceDTO) {
		Balance currentBalance = balanceRepository.findByAccountNumberAndValidToIsNull(accountNumber)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Account %s not found", accountNumber)));

		// Chiudi il record corrente
		currentBalance.setValidTo(LocalDateTime.now());
		balanceRepository.save(currentBalance);

		// Crea nuovo record
		Balance newBalance = new Balance();
		newBalance.setAccountNumber(accountNumber);
		newBalance.setAvailableBalance(balanceDTO.getAvailableBalance());
		newBalance.setAccountingBalance(balanceDTO.getAccountingBalance());
		newBalance.setBlockedAmount(balanceDTO.getBlockedAmount());
		newBalance.setLastUpdate(LocalDateTime.now());

		balanceRepository.save(newBalance);
	}

	@Transactional
	public TransactionDTO addTransaction(String accountNumber, TransactionDTO transactionDTO) {
		if (!balanceRepository.existsByAccountNumberAndValidToIsNull(accountNumber)) {
			throw new ResourceNotFoundException(String.format("Account %s not found", accountNumber));
		}

		Transaction transaction = new Transaction();
		transaction.setAccountNumber(accountNumber);
		transaction.setAmount(transactionDTO.getAmount());
		transaction
				.setDateTime(transactionDTO.getDateTime() != null ? transactionDTO.getDateTime() : LocalDateTime.now());
		transaction.setDescription(transactionDTO.getDescription());

		Transaction savedTransaction = transactionRepository.save(transaction);

		// Aggiorna il saldo
		Balance balance = balanceRepository.findByAccountNumberAndValidToIsNull(accountNumber)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Account %s not found", accountNumber)));

		updateBalance(accountNumber, calculateNewBalance(balance, transaction));

		return convertToTransactionDTO(savedTransaction);
	}

	private BalanceDTO calculateNewBalance(Balance currentBalance, Transaction transaction) {
		BalanceDTO newBalance = new BalanceDTO();
		newBalance.setAccountingBalance(currentBalance.getAccountingBalance().add(transaction.getAmount()));
		newBalance.setAvailableBalance(currentBalance.getAvailableBalance().add(transaction.getAmount()));
		newBalance.setBlockedAmount(currentBalance.getBlockedAmount());
		return newBalance;
	}
}