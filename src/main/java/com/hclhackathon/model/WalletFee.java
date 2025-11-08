package com.hclhackathon.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "wallet_fee")
public class WalletFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feeId;

    @OneToOne
    @JoinColumn(name = "txn_id", nullable = false)
    private TransactionLedger txn;

    @Column(precision = 10, scale = 2)
    private BigDecimal feeAmount;

    private LocalDateTime createdOn = LocalDateTime.now();

	public Long getFeeId() {
		return feeId;
	}

	public void setFeeId(Long feeId) {
		this.feeId = feeId;
	}

	public TransactionLedger getTxn() {
		return txn;
	}

	public void setTxn(TransactionLedger txn) {
		this.txn = txn;
	}

	public BigDecimal getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(BigDecimal feeAmount) {
		this.feeAmount = feeAmount;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

    
}
