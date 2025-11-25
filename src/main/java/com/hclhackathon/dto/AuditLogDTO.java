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
public class AuditLogDTO {

    private Long auditId;
    //private TransactionLedgerDTO txn;
    private String actionType;
    private String actor;
    private String details;
    private LocalDateTime loggedTime;
}
