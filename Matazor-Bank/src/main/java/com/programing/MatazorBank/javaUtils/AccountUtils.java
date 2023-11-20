package com.programing.MatazorBank.javaUtils;

import java.time.Year;

public class AccountUtils {
    public static final String ACCOUNT_EXISTS_CODE="101";
    public static final String ACCOUNT_EXISTS_MESSAGE="Account already exists";
    public static final String ACCOUNT_CREATIONS_CODE="002";
    public static final String ACCOUNT_CREATIONS_MESSAGE="Account has been successfully created";
    public static final String ACCOUNT_NOT_EXISTS_CODE="003";
    public static final String ACCOUNT_NOT_EXISTS_MESSAGE="User with The  provided AccountNumber are not exist";
    public static final String ACCOUNT_FOND_CODE="004";

    public static final String ACCOUNT_FOND_MESSAGE="User founded";
    public static final String ACCOUNT_CREDIT_CODE="005";
    public static final String ACCOUNT_CREDIT_MESSAGE="Credit successes";
    public static final String INSUFFICIENT_BALANCE_CODE="006";
    public static final String INSUFFICIENT_BALANCE_MESSAGE="INSUFFICIENT";
    public static final String ACCOUNT_DEBITED_SUCCESSES_CODE ="007";
    public static final String ACCOUNT_DEBITED_SUCCESSES_MESSAGE ="Account has been successfully debited";
    public static final String TRANSFER_SUCCESSES_CODE ="008";
    public static final String TRANSFER_SUCCESSES_MESSAGE ="Transfer Is Successful";
    public static String generateAccountNumber()
    {
        Year curretYear = Year.now();
        int min = 100000;
        int max = 999999;
        int radomNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);
        String year = String.valueOf(curretYear);
        String randomNumber = String.valueOf(radomNumber);
        StringBuilder accountNumber=new StringBuilder();
       return accountNumber.append(year).append(randomNumber).toString();
    }
}
