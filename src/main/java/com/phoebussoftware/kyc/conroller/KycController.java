package com.phoebussoftware.kyc.conroller;

import com.phoebussoftware.kyc.DAO.AccountRepository;
import com.phoebussoftware.kyc.DAO.CustomerRepository;
import com.phoebussoftware.kyc.model.*;
import com.phoebussoftware.kyc.web.Mapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@RestController
public class KycController {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    Mapper mapper;

    @PostMapping(value = "/addAccount", consumes = {"*/*"})
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    ResponseEntity<Account> addAccount(@Valid @RequestBody AccountDTO accountDTO) {

        Customer customer = mapper.toCustomer(accountDTO);
        Account account = mapper.toAccount(accountDTO);
        account = accountRepository.save(account);
        updateCustomerAccount(customer,account);

        return ResponseEntity.ok(account);
    }
    protected Customer updateCustomerAccount(Customer customer, Account account){
        if (customer != null)
        {
            if (customer.getAccounts() == null)
                customer.setAccounts(new HashSet<>());
            customer.getAccounts().add(account);
            customerRepository.save(customer);
        }
        return customer;
    }

    @PostMapping(value = "/addCustomer", consumes = {"*/*"})
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    ResponseEntity<Customer> addCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        Customer customer = mapper.toCustomer(customerDTO);
        customerRepository.save(customer);
        return ResponseEntity.ok(customer);
    }


    //standard exception handling
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }



}