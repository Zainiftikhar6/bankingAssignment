package com.java.assignment.banking.controller;

import com.java.assignment.banking.dto.AccountDTO;
import com.java.assignment.banking.entity.Transaction;
import com.java.assignment.banking.service.AccountService;
import com.java.assignment.banking.service.TransactionService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Account has been created.")
    })
    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) {
        AccountDTO createdAccount = accountService.createAccount(accountDTO);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account details for the given Account Number.")
    })
    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountDTO> getAccountByAccountNumber(@PathVariable String accountNumber) {
        AccountDTO accountDTO = accountService.getAccountByAccountNumber(accountNumber);
        if (accountDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Balance for the given Account Number.")
    })
    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<Double> getAccountBalance(@PathVariable String accountNumber) {
        double balance = accountService.getAccountBalance(accountNumber);
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fund Transfer Successfully.")
    })
    @PostMapping("/transfer")
    public ResponseEntity<String> transferFunds(@RequestParam String senderAccountNumber,
                                                @RequestParam String recipientAccountNumber,
                                                @RequestParam double amount) {
        transactionService.transferFunds(senderAccountNumber, recipientAccountNumber, amount);
        return new ResponseEntity<>("Funds transferred successfully", HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transaction details for the given Account Number.")
    })
    @GetMapping("/{accountNumber}/transactions")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountNumber(@PathVariable String accountNumber) {
        List<Transaction> transactions = transactionService.getTransactionsByAccountNumber(accountNumber);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}

