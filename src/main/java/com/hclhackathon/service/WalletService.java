package com.hclhackathon.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hclhackathon.model.AuditLog;
import com.hclhackathon.model.Merchant;
import com.hclhackathon.model.TransactionLedger;
import com.hclhackathon.model.Wallet;
import com.hclhackathon.model.WalletFee;
import com.hclhackathon.repository.AuditLogRepository;
import com.hclhackathon.repository.MerchantRepository;
import com.hclhackathon.repository.TransactionLedgerRepository;
import com.hclhackathon.repository.WalletFeeRepository;
import com.hclhackathon.repository.WalletRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private TransactionLedgerRepository txnRepo;

    @Autowired
    private WalletFeeRepository walletFeeRepo;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private NotificationService notificationService;

    private static final BigDecimal WALLET_FEE_PERCENTAGE = new BigDecimal("0.02"); // 2% fee

    // 1️⃣ Show Wallet + Customer + Bank Accounts
    public Wallet getWalletWithCustomerAndAccounts(Long walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        // Force fetch customer & bank accounts (if lazy)
        wallet.getCustomer().getBankAccounts().size();
        return wallet;
    }

    // 2️⃣ Process Payment
    public TransactionLedger processPayment(Long walletId, Long merchantId, BigDecimal amount, String currencyCode, String productName) {

        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new RuntimeException("Merchant not found"));

        // Validate currency
        if (!wallet.getCustomer().getCurrencyCode().equalsIgnoreCase(currencyCode)) {
            throw new RuntimeException("Invalid currency for wallet");
        }

        // Validate wallet balance
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient wallet balance");
        }

        // Deduct amount
        wallet.setBalance(wallet.getBalance().subtract(amount));
        wallet.setLastUpdated(LocalDateTime.now());
        walletRepository.save(wallet);

        // Wallet fee
        BigDecimal walletFee = amount.multiply(WALLET_FEE_PERCENTAGE);
        BigDecimal merchantCredit = amount.subtract(walletFee);

        // Transaction Ledger
        TransactionLedger txn = new TransactionLedger();
        txn.setTxnRef("TXN-" + UUID.randomUUID());
        txn.setWallet(wallet);
        txn.setMerchant(merchant);
        txn.setAmount(amount);
        txn.setCurrencyCode(currencyCode);
        txn.setTxnType("DEBIT");
        txn.setTxnStatus("SUCCESS");
        txn.setRemarks("Payment for product: " + productName);
        txn.setTxnTime(LocalDateTime.now());
        txnRepo.save(txn);

        // Wallet Fee
        WalletFee fee = new WalletFee();
        fee.setTxn(txn);
        fee.setFeeAmount(walletFee);
        fee.setCreatedOn(LocalDateTime.now());
        walletFeeRepo.save(fee);

        // Audit Log
        AuditLog audit = new AuditLog();
        audit.setTxn(txn);
        audit.setActionType("PAYMENT_PROCESSED");
        audit.setActor(wallet.getCustomer().getFullName());
        audit.setDetails("Payment of " + amount + " processed to merchant " + merchant.getMerchantName());
        audit.setLoggedTime(LocalDateTime.now());
        auditLogRepository.save(audit);

        // Notification
        notificationService.notifyMerchant(merchant, txn);

        return txn;
    }
}
