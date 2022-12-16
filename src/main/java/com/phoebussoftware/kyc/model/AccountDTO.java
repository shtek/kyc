package com.phoebussoftware.kyc.model;

import com.phoebussoftware.kyc.model.Customer;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {

    private Integer accountNumber;
    private Customer customer;
}
