package com.hclhackathon.service;

import org.springframework.stereotype.Service;

import com.hclhackathon.model.Merchant;
import com.hclhackathon.model.TransactionLedger;

@Service
public class NotificationService {
    public void notifyMerchant(Merchant merchant, TransactionLedger txn) {
        // Mock notification logic
        System.out.println("Notification sent to merchant: " + merchant.getMerchantName() +
                " for transaction: " + txn.getTxnRef() +
                ", amount: " + txn.getAmount());
    }
}

