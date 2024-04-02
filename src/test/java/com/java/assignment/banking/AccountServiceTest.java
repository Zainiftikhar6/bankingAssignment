package com.java.assignment.banking;

import com.java.assignment.banking.dto.AccountDTO;
import com.java.assignment.banking.entity.Account;
import com.java.assignment.banking.entity.Transaction;
import com.java.assignment.banking.repository.AccountRepository;
import com.java.assignment.banking.repository.TransactionRepository;
import com.java.assignment.banking.service.AccountServiceImpl;
import com.java.assignment.banking.service.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private AccountServiceImpl accountService;
    @InjectMocks
    private TransactionServiceImpl transactionService;
    @Mock
    private TransactionRepository transactionRepository;

    @Test
    public void testCreateAccount() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber("123456789");
        accountDTO.setAccountHolderName("Zain Iftikhar");
        accountDTO.setAccountType("savings");
        accountDTO.setBalance(1000.0);

        Account account = new Account();
        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setAccountHolderName(accountDTO.getAccountHolderName());
        account.setAccountType(accountDTO.getAccountType());
        account.setBalance(accountDTO.getBalance());

        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountDTO createdAccount = accountService.createAccount(accountDTO);

        assertEquals(accountDTO, createdAccount);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    public void testGetAccountBalance() {
        String accountNumber = "123456789";

        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setAccountHolderName("Zain Iftikhar");
        account.setAccountType("savings");
        account.setBalance(1000.0);

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(account);

        double balance = accountService.getAccountBalance(accountNumber);

        assertEquals(account.getBalance(), balance);
        verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
    }

    @Test
    public void testGetAccountByAccountNumber() {
        String accountNumber = "123456789";

        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setAccountHolderName("Zain Iftikhar");
        account.setAccountType("savings0");
        account.setBalance(1000.0);

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(account);

        AccountDTO retrievedAccount = accountService.getAccountByAccountNumber(accountNumber);

        assertEquals(account.getAccountNumber(), retrievedAccount.getAccountNumber());
        assertEquals(account.getAccountHolderName(), retrievedAccount.getAccountHolderName());
        assertEquals(account.getAccountType(), retrievedAccount.getAccountType());
        assertEquals(account.getBalance(), retrievedAccount.getBalance());
        verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
    }

    @Test
    public void testGetTransactionsByAccountNumber() {
        String accountNumber = "123456789";

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction());
        transactions.add(new Transaction());

        when(transactionRepository.findByAccountNumber(accountNumber)).thenReturn(transactions);

        List<Transaction> result = transactionService.getTransactionsByAccountNumber(accountNumber);

        assertEquals(transactions.size(), result.size());
    }

    @Test
    public void testTransferFunds_Success() {
        String senderAccountNumber = "123456789";
        String recipientAccountNumber = "987654321";
        double amount = 500.0;

        Account senderAccount = new Account();
        senderAccount.setAccountNumber(senderAccountNumber);
        senderAccount.setBalance(1000.0);

        Account recipientAccount = new Account();
        recipientAccount.setAccountNumber(recipientAccountNumber);
        recipientAccount.setBalance(0.0);

        when(accountRepository.findByAccountNumber(senderAccountNumber)).thenReturn(senderAccount);
        when(accountRepository.findByAccountNumber(recipientAccountNumber)).thenReturn(recipientAccount);

        transactionService.transferFunds(senderAccountNumber, recipientAccountNumber, amount);

        assertEquals(500.0, senderAccount.getBalance());
        assertEquals(500.0, recipientAccount.getBalance());

        verify(accountRepository, times(1)).findByAccountNumber(senderAccountNumber);
        verify(accountRepository, times(1)).findByAccountNumber(recipientAccountNumber);
        verify(accountRepository, times(1)).save(senderAccount);
        verify(accountRepository, times(1)).save(recipientAccount);
        verify(transactionRepository, times(2)).save(any(Transaction.class));
    }

    @Test
    public void testTransferFunds_InvalidSenderAccount() {
        String senderAccountNumber = "123456789";
        String recipientAccountNumber = "987654321";
        double amount = 500.0;

        when(accountRepository.findByAccountNumber(senderAccountNumber)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            transactionService.transferFunds(senderAccountNumber, recipientAccountNumber, amount);
        });

        verify(accountRepository, times(1)).findByAccountNumber(senderAccountNumber);
        verify(accountRepository, never()).findByAccountNumber(recipientAccountNumber);
        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }
}

