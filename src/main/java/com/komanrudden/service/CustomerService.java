package com.komanrudden.service;

import com.komanrudden.common.AccountTypeEnum;
import com.komanrudden.common.exceptions.NotFoundException;
import com.komanrudden.model.entities.AccountEntity;
import com.komanrudden.model.entities.AccountType;
import com.komanrudden.model.entities.Bank;
import com.komanrudden.model.entities.CustomerEntity;
import com.komanrudden.model.repositories.AccountRepository;
import com.komanrudden.model.repositories.AccountTypeRepository;
import com.komanrudden.model.repositories.BankRepository;
import com.komanrudden.model.repositories.CustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

import static com.komanrudden.common.ApplicationConstants.BANKX_NAME;

@Slf4j
@ApplicationScoped
public class CustomerService extends BaseService<CustomerEntity, CustomerRepository> {

    private final BankRepository bankRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final AccountRepository accountRepository;

    private static final BigDecimal SAVINGS_ACCOUNT_INITIAL_BALANCE = new BigDecimal(500);
    private static final BigDecimal CHEQUE_ACCOUNT_INITIAL_BALANCE = new BigDecimal(0);

    @Inject
    public CustomerService(CustomerRepository customerRepository,
                           AccountRepository accountRepository,
                           AccountTypeRepository accountTypeRepository,
                           BankRepository bankRepository) {
        super(customerRepository);
        this.accountRepository = accountRepository;
        this.accountTypeRepository = accountTypeRepository;
        this.bankRepository = bankRepository;
    }

    @Transactional
    public CustomerEntity create(@Valid CustomerEntity customer) {
        repository.create(customer);

        log.info("Customer created successfully: {}", customer);

        linkCustomerToAccounts(customer, getBank(customer));

        log.info("Linked customer to bank accounts successfully");

        return customer;
    }

    private void linkCustomerToAccounts(CustomerEntity customer, Bank bank) {
        List<AccountType> accountTypes = accountTypeRepository.findByTypes(List.of(AccountTypeEnum.SAVINGS.name(), AccountTypeEnum.CHEQUE.name()));
        if (accountTypes.size() != 2) {
            log.error("Cheque and Savings account types not found trying to add customer: {}", customer.getName());
            throw new NotFoundException("Failed adding new customer to BankX. Account Type not found.");
        }
        accountTypes.forEach(accountType -> accountRepository.create(createAccount(accountType, customer, bank)));
    }

    private Bank getBank(CustomerEntity customer) {
        Bank bank = bankRepository.findByName(BANKX_NAME);
        if (bank == null) {
            log.error("Bank not found: {}", BANKX_NAME);
            throw new NotFoundException(String.format("Failed adding new customer, %s, to BankX. Bank not found by name: %s", customer.getName(), BANKX_NAME));
        }
        return bank;
    }

    private AccountEntity createAccount(AccountType accountType, CustomerEntity customer, Bank bank) {
        return AccountEntity.builder()
                .accountType(accountType)
                .balance(AccountTypeEnum.SAVINGS.name()
                        .equalsIgnoreCase(accountType.getType()) ?
                        SAVINGS_ACCOUNT_INITIAL_BALANCE :
                        CHEQUE_ACCOUNT_INITIAL_BALANCE)
                .customerEntity(customer)
                .bank(bank)
                .build();
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        return super.delete(id);
    }
}
