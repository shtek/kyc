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
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer {
    private @Id @GeneratedValue  Long id;
    @NotNull(message = "forename is mandatory")
    private String forename;

    public Customer(String forename, String surname, Date dateOfBirth) {
        this.forename = forename;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;

    }

    @NotNull(message = "surname is mandatory")
    private String surname;
    @NotNull(message = "data of birth is mandatory")
    private Date dateOfBirth;
    @OneToMany(mappedBy="customer")
    private Set<Account> accounts;

    public Customer(Customer customer) {
        this.id = customer.id;
        this.forename = customer.forename;
        this.surname = customer.surname;
        this.dateOfBirth= customer.dateOfBirth;
        this.accounts = new HashSet<>();
    }
}
