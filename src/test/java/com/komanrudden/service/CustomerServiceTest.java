package com.komanrudden.service;

import com.komanrudden.common.exceptions.NotFoundException;
import com.komanrudden.model.entities.AccountEntity;
import com.komanrudden.model.entities.AccountType;
import com.komanrudden.model.entities.Bank;
import com.komanrudden.model.entities.CustomerEntity;
import com.komanrudden.model.repositories.AccountRepository;
import com.komanrudden.model.repositories.AccountTypeRepository;
import com.komanrudden.model.repositories.BankRepository;
import com.komanrudden.model.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountTypeRepository accountTypeRepository;
    @Mock
    private BankRepository bankRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("create customer successfully linked to accounts")
    void should_create_customer_successfully_linked_to_accounts() {
        Mockito.when(bankRepository.findByName(any(String.class))).thenReturn(new Bank());
        Mockito.when(accountTypeRepository.findByTypes(any(List.class))).thenReturn(List.of(new AccountType(), new AccountType()));
        CustomerEntity customer = new CustomerEntity();

        customerService.create(customer);

        verify(accountRepository, times(2)).create(any(AccountEntity.class));
    }

    @Test
    @DisplayName("create customer throws NotFoundException when bank not found")
    void createCustomerThrowsNotFoundExceptionWhenBankNotFound() {
        Mockito.when(bankRepository.findByName(any(String.class))).thenReturn(null);
        CustomerEntity customer = new CustomerEntity();

        assertThrows(NotFoundException.class, () -> customerService.create(customer));
    }

    @Test
    @DisplayName("create customer throws NotFoundException when account types not found")
    void createCustomerThrowsNotFoundExceptionWhenAccountTypesNotFound() {
        Mockito.when(bankRepository.findByName(any(String.class))).thenReturn(new Bank());
        Mockito.when(accountTypeRepository.findByTypes(any(List.class))).thenReturn(List.of());
        CustomerEntity customer = new CustomerEntity();

        assertThrows(NotFoundException.class, () -> customerService.create(customer));
    }

    @Test
    @DisplayName("delete customer successfully")
    void deleteCustomerSuccessfully() {
        long customerId = 1;
        Mockito.when(customerRepository.deleteById(customerId)).thenReturn(true);

        assertTrue(customerService.delete(customerId));
    }

    @Test
    @DisplayName("delete customer returns false when customer does not exist")
    void deleteCustomerReturnsFalseWhenCustomerDoesNotExist() {
        long customerId = 1L;
        Mockito.when(customerRepository.delete(customerId)).thenReturn(0L);

        boolean result = customerService.delete(customerId);

        assertFalse(result);
    }
}