package com.programing.MatazorBank.Service.impl;

import com.programing.MatazorBank.Dto.TransactionDTO;
import com.programing.MatazorBank.Entity.Transaction;

public interface TransactionService {
    void saveTransaction(TransactionDTO transactionDTO);
}
