package com.hclhackathon.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "audit_log")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditId;

    @ManyToOne
    @JoinColumn(name = "txn_id")
    private TransactionLedger txn;

    @Column(length = 50)
    private String actionType;

    @Column(length = 50)
    private String actor;

    @Column(length = 255)
    private String details;

    private LocalDateTime loggedTime = LocalDateTime.now();

	public Long getAuditId() {
		return auditId;
	}

	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}

	public TransactionLedger getTxn() {
		return txn;
	}

	public void setTxn(TransactionLedger txn) {
		this.txn = txn;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public LocalDateTime getLoggedTime() {
		return loggedTime;
	}

	public void setLoggedTime(LocalDateTime loggedTime) {
		this.loggedTime = loggedTime;
	}

   
}
