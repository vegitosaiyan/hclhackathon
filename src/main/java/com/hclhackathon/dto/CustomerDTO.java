package com.hclhackathon.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import com.hclhackathon.model.Merchant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private Long customerId;
    private String fullName;
    private String email;
    private String mobileNo;
    private String currencyCode;
    private LocalDateTime createdOn;
    private List<MerchantDTO> merchants;

    private List<CustomerBankAccountDTO> bankAccounts;
   // private WalletDTO wallet;
}

