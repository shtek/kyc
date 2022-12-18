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
    /*
    TODO: I am assuming that customer exist , what shall happen if customer does not exist?
     */
    ResponseEntity<Account> addAccount(@Valid @RequestBody AccountDTO accountDTO) {
        Long id =  mapper.toCustomer(accountDTO).getId();
        Customer customer = customerRepository.findById(id).orElse(null);
        Account account = mapper.toAccount(accountDTO);
        account.setCustomer(customer);
        accountRepository.save(account);
        customer.getAccounts().add(account);
        customerRepository.save(customer);

        return ResponseEntity.ok(account);
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