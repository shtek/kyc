package com.phoebussoftware.kyc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account {

    private @Id @GeneratedValue Long id;
    @NotNull(message = "account number is mandatory")
    private int accountNumber;
    @ManyToOne
    private Customer customer;
   public Account(int accountNumber, Customer customer){
        this.accountNumber = accountNumber;
        this.customer = customer;
    }

}
