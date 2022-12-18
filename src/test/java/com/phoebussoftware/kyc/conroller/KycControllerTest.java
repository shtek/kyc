package com.phoebussoftware.kyc.conroller;

import com.phoebussoftware.kyc.DAO.AccountRepository;
import com.phoebussoftware.kyc.DAO.CustomerRepository;
import com.phoebussoftware.kyc.model.Account;
import com.phoebussoftware.kyc.model.AccountDTO;
import com.phoebussoftware.kyc.model.Customer;
import com.phoebussoftware.kyc.model.CustomerDTO;
import com.phoebussoftware.kyc.web.Mapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KycControllerTest {
    @InjectMocks
    KycController kycController;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    AccountRepository accountRepository;
    @Mock
    Mapper mapper;
    @Test
    void testAddCustomer() {

        //CustomerDTO customerDTO = new CustomerDTO();
        Customer customer = new Customer();
        customer.setForename("roman");
        Customer customerSaved = new Customer();
        customerSaved.setForename("roman");

        customerSaved.setId(1L);

        //continue for surname, accounts etc
        when(mapper.toCustomer(any(CustomerDTO.class))).thenReturn(customer);


        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                ((Customer)args[0]).setId(1L);
                return customerSaved;
            }
        }).when(customerRepository).save(customer);


        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setForename("roman");
        customerDTO.setSurname("shtekelman");
        customerDTO.setDateOfBirth(new Date());
        customerDTO.setAccounts(new HashSet<>());
        ResponseEntity<Customer> responseEntity = kycController.addCustomer(customerDTO);

        assertThat(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("roman",responseEntity.getBody().getForename());
        assertEquals(1L,responseEntity.getBody().getId());

        //compare surname etc
    }
    @Test
    void testAddAccountCustomerNotNUll(){

        Customer customer = new Customer();
        customer.setForename("roman");
        Account account = new Account();
        account.setAccountNumber(1);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber(1);
        accountDTO.setCustomer(customer);

        Account accountSaved = new Account();
        accountSaved.setAccountNumber(1);
        accountSaved.setId(1L);
        when(mapper.toCustomer(any(AccountDTO.class))).thenReturn(customer);
        when(mapper.toAccount(any(AccountDTO.class))).thenReturn(account);

                doAnswer(new Answer() {
                    public Object answer(InvocationOnMock invocation) {
                        Object[] args = invocation.getArguments();
                        ((Account)args[0]).setId(1L);
                        return accountSaved;
                    }
                }).when(accountRepository).save(account);

        ResponseEntity<Account> responseEntity = kycController.addAccount(accountDTO);
        assertThat(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals(1L,responseEntity.getBody().getId());
        assertEquals(1,responseEntity.getBody().getAccountNumber());

    }
    @Test
    void testUpdateCustomerAccountCustomerIsNull(){
        assertNull(kycController.updateCustomerAccount(null, new Account()));

    }

    @Test
    void testUpdateCustomerAccountCustomerIsNotNull(){
        Customer customer = new Customer();
        Account account = new Account();
        account.setId(1L);
        Account account2 = new Account();
        account.setId(2L);

        customer.setId(1L);
        Assertions.assertNull(customer.getAccounts());
        kycController.updateCustomerAccount(customer,account);
        assertNotNull(customer);
        assertEquals(1,customer.getAccounts().size());
        kycController.updateCustomerAccount(customer,account2);
        assertEquals(2,customer.getAccounts().size());
        assertTrue(customer.getAccounts().contains(account));


    }



}