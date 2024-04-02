package com.java.assignment.banking.service;

import com.java.assignment.banking.entity.Transaction;

import java.util.List;

public interface TransactionService {
    void transferFunds(String senderAccountNumber, String recipientAccountNumber, double amount);
    List<Transaction> getTransactionsByAccountNumber(String accountNumber);
}
