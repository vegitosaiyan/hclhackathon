package com.hclhackathon.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
@Entity
@Table(name = "transaction_ledger")
@Data
public class TransactionLedger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long txnId;

    @Column(unique = true, length = 50)
    private String txnRef;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 10)
    private String currencyCode;

    @Column(length = 20)
    private String txnType;

    @Column(length = 20)
    private String txnStatus;

    @Column(length = 255)
    private String remarks;

    private LocalDateTime txnTime = LocalDateTime.now();
    
    @ManyToOne(fetch = FetchType.LAZY)  // Many transactions can belong to one wallet
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;
    
    @ManyToOne(fetch = FetchType.LAZY)  // Many transactions can belong to one merchant
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @OneToOne(mappedBy = "txn", cascade = CascadeType.ALL)
    private WalletFee fee;

    @OneToOne(mappedBy = "txn", cascade = CascadeType.ALL)
    private AuditLog auditLogs;

	
}
