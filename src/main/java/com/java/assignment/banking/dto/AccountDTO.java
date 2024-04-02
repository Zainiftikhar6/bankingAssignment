package com.java.assignment.banking.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountDTO {

    private String accountNumber;
    private String accountHolderName;
    private String accountType;
    private double balance;
}
