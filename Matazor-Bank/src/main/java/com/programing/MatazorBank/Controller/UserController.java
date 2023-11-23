package com.programing.MatazorBank.Controller;

import com.programing.MatazorBank.Dto.*;
import com.programing.MatazorBank.Service.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping(path = "/api/user")
@Tag(name="User Account management API")
public class UserController {
    @Autowired
    UserService userService;
    @Operation(
            summary = "Create new User Account",
            description = "Creating a new user and assigning an account ID"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @PostMapping
    public BankResponse CreateAccount(@RequestBody UserRequest userRequest){
        return userService.CreateAccount(userRequest);
    }
    @Operation(
            summary = "NameEnquiry",
            description = "Given an account Number,check the Name of User"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCSESS"
    )
    @GetMapping("nameEnquiry")
    public BankResponse nameEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        return (BankResponse) userService.nameEnquiry(enquiryRequest);
    }
    @Operation(
            summary = "BalanceEnquiry",
            description = "Given an account Number,check the Balance of User"
    )
    @ApiResponse(
            responseCode = "202",
            description = "Http Status 202 SUCSESS"
    )
    @GetMapping("balanceEnquiry")
    public BankResponse BalanceEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        return userService.BalanceEnquiry(enquiryRequest);
    }
    @Operation(
            summary = "Credit Account",
            description = "Given an account Number,and increase accountBalance"
    )
    @ApiResponse(
            responseCode = "203",
            description = "Http Status 203 SUCSESS"
    )
    @PostMapping("credit")
    public BankResponse creditAccount(@RequestBody CreditDebitRequest request){
        return userService.creditAccount(request);
    }
    @Operation(
            summary = "Debit account",
            description = "Given an account Number,and debit accountBalance"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Http Status 204 SUCSESS"
    )
    @PostMapping("debit")
    public BankResponse debitAccount(@RequestBody CreditDebitRequest request){
        return userService.debitAccount(request);
    }
    @Operation(
            summary = "Transfer",
            description = "Transfer amount form sourceAccountNumber to destinationAccountNumber "
    )
    @ApiResponse(
            responseCode = "205",
            description = "Http Status 205 SUCSESS"
    )
    @PostMapping("transfer")
    public BankResponse transfer(@RequestBody TransferRequest request){
        return userService.transfer(request);
    }




}
