package com.phoebussoftware.kyc.model;

import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDTO {
    private String forename;
    private String surname;
    private Date dateOfBirth;
    private Set<Account> accounts;
}
