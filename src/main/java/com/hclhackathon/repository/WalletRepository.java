package com.hclhackathon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hclhackathon.model.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    
    // Find wallet by wallet ID
    Optional<Wallet> findByWalletId(Long walletId);
    
    // Find wallet by customer ID
    Optional<Wallet> findByCustomer_CustomerId(Long customerId);
    
    // Check if wallet exists for a customer
    boolean existsByCustomer_CustomerId(Long customerId);
}
