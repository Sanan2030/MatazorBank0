package com.programing.MatazorBank.Repository;

import com.programing.MatazorBank.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,String> {

}
