package com.hclhackathon.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Table(name = "merchant")
@Data
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long merchantId;

    @Column(nullable = false, length = 100)
    private String merchantName;

    @Column(length = 100)
    private String merchantEmail;

    @Column(length = 30)
    private String bankAccountNo;

    @Column(length = 20)
    private String status = "ACTIVE";

    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL)
    private List<TransactionLedger> transactions = new ArrayList<>();

}
