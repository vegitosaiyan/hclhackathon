package com.hclhackathon.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.hclhackathon.model.Customer;
import com.hclhackathon.model.TransactionLedger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletDTO {

	 private Long walletId;
	 private CustomerDTO customer;
	 private BigDecimal balance;
	 private String status;
	 private LocalDateTime lastUpdated;
	 private List<TransactionLedgerDTO> transactions;
}
