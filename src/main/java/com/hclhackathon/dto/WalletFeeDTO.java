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
public class WalletFeeDTO {

    private Long feeId;
    //private TransactionLedgerDTO txn;
    private BigDecimal feeAmount;
    private LocalDateTime createdOn;
}
