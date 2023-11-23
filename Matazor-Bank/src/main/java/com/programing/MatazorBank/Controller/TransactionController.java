package com.programing.MatazorBank.Controller;

import com.programing.MatazorBank.Entity.Transaction;
import com.programing.MatazorBank.Service.impl.BankStatement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("bankStatement")
@AllArgsConstructor
public class TransactionController {
private BankStatement bankStatement;
@GetMapping
public List<Transaction> generateBankStatement(@RequestParam String accountNumber,
                                               @RequestParam String startDate,
                                               @RequestParam String endDate){
return bankStatement.generateStatement(accountNumber, startDate, endDate);
}

}
