package com.programing.MatazorBank.Service.impl;

import com.programing.MatazorBank.Dto.*;

public interface UserService {
    BankResponse CreateAccount(UserRequest userRequest);
    BankResponse BalanceEnquiry(EnquiryRequest enquiryRequest);
    Object nameEnquiry(EnquiryRequest request);
    BankResponse creditAccount(CreditDebitRequest request);
    BankResponse debitAccount(CreditDebitRequest request);
    BankResponse transfer(TransferRequest request);

}