package com.phoebussoftware.kyc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer {
    private @Id @GeneratedValue  Long id;
    @NotNull(message = "forename is mandatory")
    private String forename;
    @NotNull(message = "surname is mandatory")
    private String surname;
    @NotNull(message = "data of birth is mandatory")
    private Date dateOfBirth;
   @OneToMany(mappedBy="customer")
    private Set<Account> accounts;
}
