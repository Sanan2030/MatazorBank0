package com.programing.MatazorBank.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(
            name = "User Account Name"
    )
    private String AccountName;
    @Schema(
            name = "User Account balance"
    )
    private BigDecimal AccountBalance;
    @Schema(
            name = "User Account number"
    )
    private String AccountNumber;
}
