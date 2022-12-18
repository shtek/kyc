package com.phoebussoftware.kyc.DAO;

import com.phoebussoftware.kyc.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
