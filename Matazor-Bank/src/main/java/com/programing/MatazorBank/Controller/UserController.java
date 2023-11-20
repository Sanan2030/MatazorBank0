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
    @GetMapping("balanceEnquiry")
    public BankResponse BalanceEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        return userService.BalanceEnquiry(enquiryRequest);
    }
    @PostMapping("credit")
    public BankResponse creditAccount(@RequestBody CreditDebitRequest request){
        return userService.creditAccount(request);
    }
    @PostMapping("debit")
    public BankResponse debitAccount(@RequestBody CreditDebitRequest request){
        return userService.debitAccount(request);
    }
    @PostMapping("transfer")
    public BankResponse transfer(@RequestBody TransferRequest request){
        return userService.transfer(request);
    }




}
