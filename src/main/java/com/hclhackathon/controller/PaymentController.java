package com.hclhackathon.controller;

import com.hclhackathon.dto.PaymentRequest;
import com.hclhackathon.model.TransactionLedger;
import com.hclhackathon.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Payment", description = "Payment processing APIs")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(
        summary = "Process a payment",
        description = "Process a payment from customer's wallet to merchant")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Payment processed successfully",
            content = @Content(schema = @Schema(implementation = TransactionLedger.class))),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid payment request or insufficient funds",
            content = @Content()),
        @ApiResponse(
            responseCode = "404", 
            description = "Customer wallet or merchant not found",
            content = @Content())
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionLedger> processPayment(@Valid @RequestBody PaymentRequest paymentRequest) {
        TransactionLedger transaction = paymentService.processPayment(paymentRequest);
        return ResponseEntity.ok(transaction);
    }
}
