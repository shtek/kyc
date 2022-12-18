package com.phoebussoftware.kyc.DAO;

import com.phoebussoftware.kyc.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {}

