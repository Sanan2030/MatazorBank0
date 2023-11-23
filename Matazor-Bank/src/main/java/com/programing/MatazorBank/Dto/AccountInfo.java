package com.programing.MatazorBank.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountInfo {
    private String AccountName;
    private BigDecimal AccountBalance;
    private String AccountNumber;
}