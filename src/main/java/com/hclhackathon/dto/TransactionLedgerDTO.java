package com.hclhackathon.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.hclhackathon.model.AuditLog;
import com.hclhackathon.model.Customer;
import com.hclhackathon.model.CustomerBankAccount;
import com.hclhackathon.model.Merchant;
import com.hclhackathon.model.Wallet;
import com.hclhackathon.model.WalletFee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionLedgerDTO {

    private Long txnId;
    private String txnRef;
    private BigDecimal amount;
    private String currencyCode;
    private String txnType;
    private String txnStatus;
    private String remarks;
    private LocalDateTime txnTime;
    private WalletFeeDTO fee;
    private List<AuditLogDTO> auditLogs;
}
