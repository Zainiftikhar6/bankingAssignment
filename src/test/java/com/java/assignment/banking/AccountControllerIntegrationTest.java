package com.java.assignment.banking;

import com.java.assignment.banking.dto.AccountDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateAccount() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber("123456789");
        accountDTO.setAccountHolderName("Zain Iftikhar");
        accountDTO.setAccountType("Savings");
        accountDTO.setBalance(1000.0);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\":\"123456789\",\"accountHolderName\":\"Zain Iftikhar\",\"accountType\":\"Savings\",\"balance\":1000.0}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());
    }

    @Test
    public void testTransferFunds() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions/transfer")
                        .param("senderAccountNumber", "123456789")
                        .param("recipientAccountNumber", "018669621")
                        .param("amount", "500.0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }
    // Add more integration tests for other endpoints in AccountController
}

