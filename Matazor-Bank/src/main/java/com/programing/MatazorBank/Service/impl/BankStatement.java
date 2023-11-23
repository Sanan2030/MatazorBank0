package com.programing.MatazorBank.Service.impl;

import com.programing.MatazorBank.Entity.Transaction;
import com.programing.MatazorBank.Repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@AllArgsConstructor
public class BankStatement {
   private TransactionRepository transactionRepository;
   public List<Transaction> generateStatement(String accountNumber,String startDate,String endDate){
       LocalDate start=LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
       LocalDate end=LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
       List<Transaction> transactionList= transactionRepository.findAll().stream().
               filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
               .filter(transaction -> transaction.getCreatedAt().isEqual(start))
               .filter(transaction -> transaction.getCreatedAt().isEqual(end)).toList();
       return transactionList;
   }

}
