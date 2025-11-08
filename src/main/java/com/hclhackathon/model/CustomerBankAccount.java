package com.hclhackathon.model;
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
@Table(name = "customer_bank_account", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"customer_id", "is_primary"})
})
public class CustomerBankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false, length = 100)
    private String bankName;

    @Column(nullable = false, length = 34)
    private String accountNumber;

    @Column(length = 20)
    private String ifscCode;

    @Column(length = 20)
    private String accountType;

    @Column(nullable = false)
    private Boolean isPrimary = false;

    @Column(length = 20)
    private String status = "ACTIVE";

    private LocalDateTime linkedOn = LocalDateTime.now();

    private LocalDateTime lastUsedOn;

    @Column(length = 50)
    private String nickname;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Boolean getIsPrimary() {
		return isPrimary;
	}

	public void setIsPrimary(Boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getLinkedOn() {
		return linkedOn;
	}

	public void setLinkedOn(LocalDateTime linkedOn) {
		this.linkedOn = linkedOn;
	}

	public LocalDateTime getLastUsedOn() {
		return lastUsedOn;
	}

	public void setLastUsedOn(LocalDateTime lastUsedOn) {
		this.lastUsedOn = lastUsedOn;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

    
    
}
