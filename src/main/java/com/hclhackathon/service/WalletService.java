package com.hclhackathon.service;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.hclhackathon.dto.AuditLogDTO;
import com.hclhackathon.dto.MerchantDTO;
import com.hclhackathon.dto.TransactionLedgerDTO;
import com.hclhackathon.dto.WalletDTO;
import com.hclhackathon.dto.WalletFeeDTO;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

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
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private Executor taskExecutor;

    private static final BigDecimal WALLET_FEE_PERCENTAGE = new BigDecimal("0.02"); // 2% fee

    // 1️⃣ Show Wallet + Customer + Bank Accounts
    public WalletDTO getWalletWithCustomerAndAccounts(Long walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        // Force fetch customer & bank accounts (if lazy)
        wallet.getCustomer().getBankAccounts().size();
        // Fetch all merchants (no direct relation)
        List<Merchant> allMerchants = merchantRepository.findAll();
        Type listType = new TypeToken<List<MerchantDTO>>() {}.getType();
        List<MerchantDTO> merchantDTO = modelMapper.map(allMerchants, listType);
        WalletDTO walletDTO = modelMapper.map(wallet, WalletDTO.class);
        walletDTO.getCustomer().setMerchants(merchantDTO);
        return walletDTO;
    }

    @Transactional(rollbackOn = Exception.class)
    @Async
    public CompletableFuture<TransactionLedgerDTO> processPayment(
            Long walletId, Long merchantId, BigDecimal amount, String currencyCode, String productName) {
    	
            return CompletableFuture.supplyAsync(() ->
                    processPaymentTransactional(walletId, merchantId, amount, currencyCode, productName),taskExecutor
            );
        
     }
    
    @Transactional
    protected TransactionLedgerDTO processPaymentTransactional(
            Long walletId,
            Long merchantId,
            BigDecimal amount,
            String currencyCode,
            String productName
    ) {
        // Fetch wallet and merchant
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new RuntimeException("Merchant not found"));

        // Validate currency
        if (!wallet.getCustomer().getCurrencyCode().equalsIgnoreCase(currencyCode)) {
            throw new RuntimeException("Invalid currency for wallet");
        }

        // Validate balance
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient wallet balance");
        }

        // Deduct wallet balance
        wallet.setBalance(wallet.getBalance().subtract(amount));
        wallet.setLastUpdated(LocalDateTime.now());
        walletRepository.save(wallet);

        // Transaction Ledger
        TransactionLedger txn = new TransactionLedger();
        txn.setTxnRef(productName + "-" + UUID.randomUUID());
        txn.setWallet(wallet);
        txn.setMerchant(merchant);
        txn.setAmount(amount);
        txn.setCurrencyCode(currencyCode);
        txn.setTxnType("DEBIT");
        txn.setTxnStatus("SUCCESS");
        txn.setRemarks("Payment for product: " + productName);
        txn.setTxnTime(LocalDateTime.now());
        txn = txnRepo.save(txn); // save first to get ID

        // Wallet Fee
        WalletFee fee = new WalletFee();
        fee.setTxn(txn); // link properly
        fee.setFeeAmount(amount.multiply(WALLET_FEE_PERCENTAGE));
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

        // Notification (can also be async but outside transaction)
        notificationService.notifyMerchant(merchant, txn);

        // Map to DTO
        TransactionLedgerDTO dto = modelMapper.map(txn, TransactionLedgerDTO.class);
        dto.setFee(modelMapper.map(fee, WalletFeeDTO.class));
        dto.setAuditLogs(modelMapper.map(audit, AuditLogDTO.class));

        return dto;
    }


}
