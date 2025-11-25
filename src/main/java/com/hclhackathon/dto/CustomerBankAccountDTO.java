package com.hclhackathon.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerBankAccountDTO {
	private Long accountId;
    private String bankName;
    private String accountNumber;
    private String ifscCode;
    private String accountType;
    private boolean isPrimary;
    private String status;
    private LocalDateTime linkedOn;
    private LocalDateTime lastUsedOn;
    private String nickname;
}
