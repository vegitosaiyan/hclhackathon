package com.hclhackathon.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "wallet_fee")
@Data
public class WalletFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feeId;

    @OneToOne
    @JoinColumn(name = "txn_id")
    private TransactionLedger txn;

    @Column(precision = 10, scale = 2)
    private BigDecimal feeAmount;

    private LocalDateTime createdOn = LocalDateTime.now();

	
}
