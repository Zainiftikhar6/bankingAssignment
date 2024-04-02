package com.java.assignment.banking.service;

import com.java.assignment.banking.entity.Account;
import com.java.assignment.banking.entity.Transaction;
import com.java.assignment.banking.repository.AccountRepository;
import com.java.assignment.banking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    @Transactional
    public void transferFunds(String senderAccountNumber, String recipientAccountNumber, double amount) {
        Account senderAccount = accountRepository.findByAccountNumber(senderAccountNumber);
        Account recipientAccount = accountRepository.findByAccountNumber(recipientAccountNumber);

        if (senderAccount == null) {
            throw new IllegalArgumentException("Invalid sender  account number");
        }

        if (recipientAccount == null) {
            throw new IllegalArgumentException("Invalid recipient account number");
        }

        if (senderAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds in sender account");
        }

        senderAccount.setBalance(senderAccount.getBalance() - amount);
        recipientAccount.setBalance(recipientAccount.getBalance() + amount);

        accountRepository.save(senderAccount);
        accountRepository.save(recipientAccount);

        Transaction senderTransaction = new Transaction();
        senderTransaction.setAccountNumber(senderAccountNumber);
        senderTransaction.setTransactionType("DEBIT");
        senderTransaction.setAmount(amount);
        senderTransaction.setTimestamp(new Date());
        transactionRepository.save(senderTransaction);

        Transaction recipientTransaction = new Transaction();
        recipientTransaction.setAccountNumber(recipientAccountNumber);
        recipientTransaction.setTransactionType("CREDIT");
        recipientTransaction.setAmount(amount);
        recipientTransaction.setTimestamp(new Date());
        transactionRepository.save(recipientTransaction);
    }

    @Override
    public List<Transaction> getTransactionsByAccountNumber(String accountNumber) {
        return transactionRepository.findByAccountNumber(accountNumber);
    }
}

