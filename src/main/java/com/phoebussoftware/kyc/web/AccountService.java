package com.phoebussoftware.kyc.web;

import com.phoebussoftware.kyc.model.Account;
import com.phoebussoftware.kyc.model.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    AccountRepository repository;
    public void saveOrUpdate(Account account) {
        repository.save(account);
    }

}
