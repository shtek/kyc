package com.phoebussoftware.kyc.web;

import com.phoebussoftware.kyc.model.AccountDTO;
import com.phoebussoftware.kyc.model.Account;
import org.springframework.stereotype.Component;

@Component
public class Mapper {


    public Account toAccount(AccountDTO accountDTO) {
        return new Account(accountDTO.getAccountNumber(),accountDTO.getCustomer());
    }
}