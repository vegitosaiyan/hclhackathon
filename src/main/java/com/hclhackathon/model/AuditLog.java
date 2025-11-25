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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "audit_log")
@Data
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditId;

    @OneToOne
    @JoinColumn(name = "txn_id")
    private TransactionLedger txn;

    @Column(length = 50)
    private String actionType;

    @Column(length = 50)
    private String actor;

    @Column(length = 255)
    private String details;

    private LocalDateTime loggedTime = LocalDateTime.now();

	
}
