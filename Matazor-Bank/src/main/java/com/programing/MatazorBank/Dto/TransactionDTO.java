package com.programing.MatazorBank.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class TransactionDTO {
    private String transactionType;
    private BigDecimal amount;
    private String accountNumber;
    private String Status;

}
