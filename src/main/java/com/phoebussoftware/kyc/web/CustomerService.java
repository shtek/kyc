package com.phoebussoftware.kyc.web;

import com.phoebussoftware.kyc.model.Account;

import com.phoebussoftware.kyc.model.Customer;
import com.phoebussoftware.kyc.model.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository repository;

    public void save(Customer customer) {
        repository.save(customer);
    }
    public Customer find(Long customerId) {
       return repository.findById(customerId).orElse(null);
    }
}
