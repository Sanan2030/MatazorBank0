package com.programing.MatazorBank.Service.impl;

import com.programing.MatazorBank.Dto.*;
import com.programing.MatazorBank.Entity.Role;
import com.programing.MatazorBank.Entity.User;
import com.programing.MatazorBank.Repository.UserRepository;
import com.programing.MatazorBank.config.JwtTokenPProvider;
import com.programing.MatazorBank.javaUtils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailService;
   @Autowired
   TransactionService transactionService;
   @Autowired
    PasswordEncoder passwordEncoder;
   @Autowired
   AuthenticationManager authenticationManager;
   @Autowired
    JwtTokenPProvider jwtTokenPProvider;


    private BankResponse createErrorResponse() {
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                .accountInfo(null)
                .build();
    }

    @Override
    public BankResponse CreateAccount(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .PhoneNumber(userRequest.getPhoneNumber())
                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                .Status("Active")
                .role(Role.ROLE_Admin)
                .build();
        User savedUser = userRepository.save(newUser);
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("ACCOUNT CREATION")
                .MessageBody("Congratulations! Your account has " + "been successfully created\n+" +
                        "Your account details:\n" + "Account Name:" + savedUser.getFirstName() + " " +
                        savedUser.getLastName() + " " + savedUser.getOtherName() + "\nAccount Number:" +
                        savedUser.getAccountNumber()).build();
        emailService.SendEmail(emailDetails);
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATIONS_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREATIONS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .AccountBalance(savedUser.getAccountBalance())
                        .AccountNumber(savedUser.getAccountNumber())
                        .AccountName(savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName())
                        .build())
                .build();
    }
    public BankResponse login(LoginDTO loginDTO){
        Authentication authentication;
        authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword()));
        EmailDetails.EmailDetailsBuilder builder = EmailDetails.builder();
        builder.subject("You logged in !");
        builder.recipient(loginDTO.getEmail());
        builder.MessageBody("You logged into your account. If you didn't make this request,contact with MatazorBank");
        EmailDetails loginAlert= builder
                .build();
        emailService.SendEmail(loginAlert);
        return BankResponse.builder()
        .responseCode("Login Success")
                        .responseMessage(jwtTokenPProvider.generateToken(authentication))
                .build();

    }

    @Override
    public BankResponse BalanceEnquiry(EnquiryRequest request) {
        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist) {
            return createErrorResponse();
        }
        User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_FOND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOND_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .AccountBalance(foundUser.getAccountBalance())
                        .AccountNumber(request.getAccountNumber())
                        .AccountName(foundUser.getFirstName() + " " +
                                foundUser.getLastName() + " " + foundUser.getOtherName())
                        .build())
                .build();
    }

    @Override
    public BankResponse nameEnquiry(EnquiryRequest request) {
        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist) {
            return createErrorResponse();
        }
        User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
        return BankResponse.builder().accountInfo(AccountInfo.builder()
                        .AccountName(foundUser.getFirstName() + " " +
                                foundUser.getLastName() + " " + foundUser.getOtherName()).build()).build();
    }

    @Override
    public BankResponse creditAccount(CreditDebitRequest request) {
        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist) {
            return createErrorResponse();
        }
        User userToCredit = userRepository.findByAccountNumber(request.getAccountNumber());
        userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(request.getAmount()));
        userRepository.save(userToCredit);
        TransactionDTO transactionDTO=TransactionDTO.builder()
                .accountNumber(request.getAccountNumber())
                .transactionType("CREDIT")
                .amount(request.getAmount())
                .Status("SUCCESSFUL")
            .build();
        transactionService.saveTransaction(transactionDTO);
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREDIT_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREDIT_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .AccountName(userToCredit.getFirstName() + " " + userToCredit.getLastName() + " " + userToCredit.getOtherName())
                        .AccountBalance(userToCredit.getAccountBalance())
                        .AccountNumber(request.getAccountNumber())
                        .build())
                .build();
    }

    @Override
    public BankResponse debitAccount(CreditDebitRequest request) {
        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist) {
            return createErrorResponse();
        }
        User UserToDebit = userRepository.findByAccountNumber(request.getAccountNumber());
        BigInteger availableBalance = UserToDebit.getAccountBalance().toBigInteger();
        BigInteger debitAmount = request.getAmount().toBigInteger();
        if (availableBalance.intValue() < debitAmount.intValue()) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        } else {
            UserToDebit.setAccountBalance(UserToDebit.getAccountBalance().subtract(request.getAmount()));
            userRepository.save(UserToDebit);
            TransactionDTO transactionDTO=TransactionDTO.builder()
                    .accountNumber(request.getAccountNumber())
                    .transactionType("DEBIT")
                    .amount(request.getAmount())
                    .Status("SUCCESSFUL")
                    .build();
            transactionService.saveTransaction(transactionDTO);
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESSES_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_DEBITED_SUCCESSES_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .AccountNumber(request.getAccountNumber())
                            .AccountName(UserToDebit.getFirstName() + " " + UserToDebit.getLastName() + " " + UserToDebit.getOtherName())
                            .AccountBalance(UserToDebit.getAccountBalance())
                            .build())
                    .build();
        }
    }

    @Override
    public BankResponse transfer(TransferRequest request) {
        boolean isDestinationAccountExist = userRepository.existsByAccountNumber(request.getDestinationAccountNumber());
        if (!isDestinationAccountExist) {
            return createErrorResponse();
        }
        User sourceAccountUser = userRepository.findByAccountNumber(request.getSourceAccountNumber());
        if (request.getAmount().compareTo(sourceAccountUser.getAccountBalance()) > 0) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        sourceAccountUser.setAccountBalance(sourceAccountUser.getAccountBalance().subtract(request.getAmount()));
        userRepository.save(sourceAccountUser);
        String SourceUsername= sourceAccountUser.getFirstName()+sourceAccountUser.getLastName()+sourceAccountUser.getOtherName();
        EmailDetails debitAlert= EmailDetails.builder()
                .subject("Debit  Alert")
                .recipient(sourceAccountUser.getEmail())
                .MessageBody("The sum of "+request.getAmount()+" has been deducted from your account! Your current balance is "+sourceAccountUser.getAccountBalance())
                .build();
        emailService.SendEmail(debitAlert);
        User destinationAccountUser=userRepository.findByAccountNumber(request.getDestinationAccountNumber());
        destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(request.getAmount()));

        userRepository.save(destinationAccountUser);
        EmailDetails creditAlert= EmailDetails.builder()
                .subject("Credit  Alert")
                .recipient(sourceAccountUser.getEmail())
                .MessageBody("The sum of "+request.getAmount()+" has been sent to your account from "+SourceUsername+" Your current balance is "+sourceAccountUser.getAccountBalance())
                .build();
        emailService.SendEmail(creditAlert);
        TransactionDTO transactionDTO=TransactionDTO.builder()
                .accountNumber(request.getDestinationAccountNumber())
                .transactionType("CREDIT")
                .amount(request.getAmount())
                .build();
        transactionService.saveTransaction(transactionDTO);
        return BankResponse.builder()
                .responseCode(AccountUtils.TRANSFER_SUCCESSES_CODE)
                .responseMessage(AccountUtils.TRANSFER_SUCCESSES_MESSAGE)
                .accountInfo(null)
                .build();

    }
}
