package com.phoebussoftware.kyc.conroller;

import com.phoebussoftware.kyc.DAO.CustomerRepository;
import com.phoebussoftware.kyc.model.Account;
import com.phoebussoftware.kyc.model.Customer;
import com.phoebussoftware.kyc.model.CustomerDTO;
import com.phoebussoftware.kyc.web.Mapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
class KycControllerTest {
    @InjectMocks
    KycController kycController;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    Mapper mapper;
    @Test
    void testAddCustomer() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        CustomerDTO customerDTO = new CustomerDTO();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setForename("roman");
        //continue for surname, accounts etc
        when(mapper.toCustomer(any(CustomerDTO.class))).thenReturn(customer);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO customerDTOtoAdd = new CustomerDTO();
        customerDTOtoAdd.setForename("roman");
        customerDTOtoAdd.setSurname("shtekelman");
        customerDTOtoAdd.setDateOfBirth(new Date());
        customerDTOtoAdd.setAccounts(new HashSet<>());
        ResponseEntity<Customer> responseEntity = kycController.addCustomer(customerDTOtoAdd);

        assertThat(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("roman",responseEntity.getBody().getForename());
        //compare surname etc
    }

}