package com.phoebussoftware.kyc.web;

import com.phoebussoftware.kyc.model.AccountDTO;
import com.phoebussoftware.kyc.model.Account;
import com.phoebussoftware.kyc.model.Customer;
import com.phoebussoftware.kyc.model.CustomerDTO;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class Mapper {


    public Account toAccount(AccountDTO accountDTO) {

        return new Account(accountDTO.getAccountNumber(),accountDTO.getCustomer());
    }
    public Customer toCustomer(AccountDTO accountDTO) {

        return new Customer(accountDTO.getCustomer());
    }
    public Customer toCustomer(CustomerDTO customerDTO){
        return  new Customer(customerDTO.getForename(), customerDTO.getSurname(), customerDTO.getDateOfBirth());
    }
}