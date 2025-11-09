package com.hclhackathon.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name = "customer_bank_account", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"customer_id", "is_primary"})
})
@Data
public class CustomerBankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @ManyToOne(fetch = FetchType.LAZY)
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

	
    
}
