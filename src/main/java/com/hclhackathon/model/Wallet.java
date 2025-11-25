package com.hclhackathon.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
import lombok.Data;
@Entity
@Table(name = "wallet")
@Data
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletId;

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonBackReference
    private Customer customer;

    @Column(precision = 12, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(length = 20)
    private String status = "ACTIVE";

    private LocalDateTime lastUpdated = LocalDateTime.now();
    
    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
    private List<TransactionLedger> transactions = new ArrayList<>();

	
   
}
