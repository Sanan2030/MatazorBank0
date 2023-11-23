package com.programing.MatazorBank.Service.impl;

import com.programing.MatazorBank.Dto.TransactionDTO;
import com.programing.MatazorBank.Entity.Transaction;
import com.programing.MatazorBank.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class TransactionServiceImpl implements TransactionService{
    @Autowired
    TransactionRepository transactionRepository;
    @Override
    public void saveTransaction(TransactionDTO transactionDTO) {
        Transaction transaction=Transaction.builder()
        .transactionType(transactionDTO.getTransactionType())
                .accountNumber(transactionDTO.getAccountNumber())
                .amount(transactionDTO.getAmount())
                .Status("SUCSESS")
                .build();
transactionRepository.save(transaction);
System.out.println("Transaction Saved");
    }
}
