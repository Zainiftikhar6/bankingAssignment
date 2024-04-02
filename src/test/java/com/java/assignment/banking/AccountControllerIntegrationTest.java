package com.java.assignment.banking;

import com.java.assignment.banking.dto.AccountDTO;
import com.java.assignment.banking.entity.Transaction;
import com.java.assignment.banking.service.AccountService;
import com.java.assignment.banking.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountService accountService;
    @MockBean
    private TransactionService transactionService;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testCreateAccount() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber("123456789");
        accountDTO.setAccountHolderName("Zain");
        accountDTO.setAccountType("Savings");
        accountDTO.setBalance(1000.0);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\":\"123456789\",\"accountHolderName\":\"Zain Iftikhar\",\"accountType\":\"Savings\",\"balance\":1000.0}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());
    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testGetAccountByAccountNumber() throws Exception {
        String accountNumber = "123456789";
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber(accountNumber);
        accountDTO.setAccountHolderName("Zain");
        accountDTO.setAccountType("Savings");
        accountDTO.setBalance(1000.0);

        Mockito.when(accountService.getAccountByAccountNumber(accountNumber)).thenReturn(accountDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/{accountNumber}", accountNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").value(accountNumber))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountHolderName").value("Zain"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountType").value("Savings"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(1000.0));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testGetAccountByAccountNumber_NotFound() throws Exception {
        String accountNumber = "123456789";

        Mockito.when(accountService.getAccountByAccountNumber(accountNumber)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/{accountNumber}", accountNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testGetAccountBalance() throws Exception {
        String accountNumber = "123456789";
        double balance = 1000.0;

        // Mock the service method to return the balance
        Mockito.when(accountService.getAccountBalance(accountNumber)).thenReturn(balance);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/{accountNumber}/balance", accountNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(String.valueOf(balance)));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testTransferFunds() throws Exception {
        String senderAccountNumber = "123456789";
        String recipientAccountNumber = "987654321";
        double amount = 500.0;

        // Mock the service method to do nothing (simulate successful transfer)
        Mockito.doNothing().when(transactionService).transferFunds(senderAccountNumber, recipientAccountNumber, amount);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts/transfer")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("senderAccountNumber", senderAccountNumber)
                        .param("recipientAccountNumber", recipientAccountNumber)
                        .param("amount", String.valueOf(amount)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Funds transferred successfully"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testGetTransactionsByAccountNumber() throws Exception {
        String accountNumber = "123456789";

        List<Transaction> transactions = new ArrayList<>();
        // Add some dummy transactions to the list
        transactions.add(new Transaction());
        transactions.add(new Transaction());

        Mockito.when(transactionService.getTransactionsByAccountNumber(accountNumber)).thenReturn(transactions);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/{accountNumber}/transactions", accountNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(transactions.size()));
    }
}

