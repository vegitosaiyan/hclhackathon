package com.hclhackathon.service.impl;

import com.hclhackathon.dto.PaymentRequest;
import com.hclhackathon.exception.InsufficientFundsException;
import com.hclhackathon.exception.NotFoundException;
import com.hclhackathon.model.*;
import com.hclhackathon.repository.MerchantRepository;
import com.hclhackathon.repository.TransactionLedgerRepository;
import com.hclhackathon.repository.WalletRepository;
import com.hclhackathon.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final WalletRepository walletRepository;
    private final MerchantRepository merchantRepository;
    private final TransactionLedgerRepository transactionLedgerRepository;

    @Override
    @Transactional
    public TransactionLedger processPayment(PaymentRequest paymentRequest) {
        // 1. Validate and fetch wallet and merchant
        Wallet wallet = walletRepository.findByCustomer_CustomerId(paymentRequest.getCustomerId())
                .orElseThrow(() -> new NotFoundException("Wallet not found for customer: " + paymentRequest.getCustomerId()));

        Merchant merchant = merchantRepository.findById(paymentRequest.getMerchantId())
                .orElseThrow(() -> new NotFoundException("Merchant not found: " + paymentRequest.getMerchantId()));

        // 2. Check if wallet has sufficient balance
        if (wallet.getBalance().compareTo(paymentRequest.getAmount()) < 0) {
            throw new InsufficientFundsException("Insufficient funds in wallet");
        }

        // 3. Deduct amount from wallet
        wallet.setBalance(wallet.getBalance().subtract(paymentRequest.getAmount()));
        walletRepository.save(wallet);

        // 4. Create transaction ledger entry
        TransactionLedger transaction = new TransactionLedger();
        transaction.setTxnRef(generateTransactionReference());
        transaction.setWallet(wallet);
        transaction.setMerchant(merchant);
        transaction.setAmount(paymentRequest.getAmount());
        transaction.setCurrencyCode(paymentRequest.getCurrencyCode());
        transaction.setTxnType("PAYMENT");
        transaction.setTxnStatus("COMPLETED");
        transaction.setTxnTime(LocalDateTime.now());
        transaction.setRemarks(paymentRequest.getDescription());
        
        // 5. Save the transaction
        return transactionLedgerRepository.save(transaction);
    }

    private String generateTransactionReference() {
        return "TXN" + UUID.randomUUID().toString().replace("-", "").substring(0, 15).toUpperCase();
    }
}
