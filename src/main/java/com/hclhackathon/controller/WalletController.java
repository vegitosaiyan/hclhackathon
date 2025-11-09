package com.hclhackathon.controller;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hclhackathon.dto.TransactionLedgerDTO;
import com.hclhackathon.dto.WalletDTO;
import com.hclhackathon.model.TransactionLedger;
import com.hclhackathon.model.Wallet;
import com.hclhackathon.service.WalletService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/wallets")
@Slf4j
public class WalletController {

    @Autowired
    private WalletService walletService;

    // 1️⃣ Show wallet + customer + accounts
    @GetMapping("/{walletId}")
    public ResponseEntity<WalletDTO> getWalletDetails(@PathVariable Long walletId) {
        try {
            WalletDTO wallet = walletService.getWalletWithCustomerAndAccounts(walletId);
            return ResponseEntity.ok(wallet);
        } catch (Exception e) {
            log.error("Error fetching wallet details", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 2️⃣ Process payment
    @PostMapping("/pay")
    @Async
    public CompletableFuture<TransactionLedgerDTO> payMerchant(
            @RequestParam Long walletId,
            @RequestParam Long merchantId,
            @RequestParam BigDecimal amount,
            @RequestParam String currency,
            @RequestParam String productName) {

            return walletService.processPayment(walletId, merchantId, amount, currency, productName);
    }
}

