package com.hclhackathon.service;

import com.hclhackathon.dto.PaymentRequest;
import com.hclhackathon.model.TransactionLedger;

public interface PaymentService {
    TransactionLedger processPayment(PaymentRequest paymentRequest);
}
