package com.phoebussoftware.kyc.model;

import com.phoebussoftware.kyc.model.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {
    private int accountNumber;
    private Customer customer;
}
