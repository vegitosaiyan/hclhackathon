package com.hclhackathon.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
@Entity
@Table(name = "transaction_ledger")
public class TransactionLedger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long txnId;

    @Column(unique = true, length = 50)
    private String txnRef;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private CustomerBankAccount bankAccount;

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

    @OneToOne(mappedBy = "txn", cascade = CascadeType.ALL)
    private WalletFee fee;

    @OneToMany(mappedBy = "txn", cascade = CascadeType.ALL)
    private List<AuditLog> auditLogs;

	public Long getTxnId() {
		return txnId;
	}

	public void setTxnId(Long txnId) {
		this.txnId = txnId;
	}

	public String getTxnRef() {
		return txnRef;
	}

	public void setTxnRef(String txnRef) {
		this.txnRef = txnRef;
	}

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public CustomerBankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(CustomerBankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getTxnStatus() {
		return txnStatus;
	}

	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public LocalDateTime getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(LocalDateTime txnTime) {
		this.txnTime = txnTime;
	}

	public WalletFee getFee() {
		return fee;
	}

	public void setFee(WalletFee fee) {
		this.fee = fee;
	}

	public List<AuditLog> getAuditLogs() {
		return auditLogs;
	}

	public void setAuditLogs(List<AuditLog> auditLogs) {
		this.auditLogs = auditLogs;
	}

  
}
