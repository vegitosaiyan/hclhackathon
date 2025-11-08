package com.hclhackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hclhackathon.model.WalletFee;

@Repository
public interface WalletFeeRepository extends JpaRepository<WalletFee, Long> {}
