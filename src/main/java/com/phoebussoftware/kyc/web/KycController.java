package com.phoebussoftware.kyc.web;

import com.phoebussoftware.kyc.model.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class KycController {
    @Autowired
    AccountService accountService;
    @Autowired
    CustomerService customerService;
    @Autowired
    Mapper mapper;

    @PostMapping(value = "/addAccount", consumes = {"*/*"})
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    ResponseEntity<Account> addAccount(@Valid @RequestBody AccountDTO accountDTO) {
        Long id =  mapper.toCustomer(accountDTO).getId();
        Customer customer = customerService.find(id);
         if (customer == null)
         {
             customer =  mapper.toCustomer(accountDTO);

         }
        Account account = mapper.toAccount(accountDTO);
        customer.getAccounts().add(account);
        customerService.save(customer);
        account.setCustomer(customer);
        accountService.save(account);

        return ResponseEntity.ok(account);
    }
    @PostMapping(value = "/addCustomer", consumes = {"*/*"})
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    ResponseEntity<Customer> addCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        Customer customer = mapper.toCustomer(customerDTO);
        customerService.save(customer);
        return ResponseEntity.ok(customer);
    }
    @Autowired
    AccountRepository repository;
 /*
 //TODO: remove this method
  */
    @GetMapping(value = "/findAccount/{id}")
 Account getAccount(@PathVariable Long id) {
     System.out.println("id" +id);
     Account a = new Account(2,null);
     Account b = repository.findById(id).orElse(a);
     return  repository.findById(id).orElse(a);
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