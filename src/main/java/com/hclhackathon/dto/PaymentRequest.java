package com.hclhackathon.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO for processing payment requests from customer to merchant.
 */
@Data
@Schema(description = "Payment request details")
public class PaymentRequest {
    
    @Schema(description = "ID of the customer making the payment", example = "1")
    @NotNull(message = "Customer ID is required")
    private Long customerId;
    
    @Schema(description = "ID of the merchant receiving the payment", example = "1")
    @NotNull(message = "Merchant ID is required")
    private Long merchantId;
    
    @Schema(description = "Payment amount", example = "100.50")
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;
    
    @Schema(description = "Currency code in ISO 4217 format", example = "USD")
    @NotNull(message = "Currency code is required")
    private String currencyCode;
    
    @Schema(description = "Optional payment description", example = "Payment for monthly subscription")
    private String description;
    
    /**
     * Default constructor
     */
    public PaymentRequest() {
        // Default constructor
    }
    
    /**
     * Parameterized constructor
     */
    public PaymentRequest(Long customerId, Long merchantId, BigDecimal amount, String currencyCode, String description) {
        this.customerId = customerId;
        this.merchantId = merchantId;
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.description = description;
    }
}
