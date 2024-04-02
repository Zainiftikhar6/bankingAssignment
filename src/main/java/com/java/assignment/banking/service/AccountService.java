package com.java.assignment.banking.service;


import com.java.assignment.banking.dto.AccountDTO;

public interface AccountService {
    AccountDTO createAccount(AccountDTO accountDTO);
    AccountDTO getAccountByAccountNumber(String accountNumber);
    double getAccountBalance(String accountNumber);
}
