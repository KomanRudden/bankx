package com.komanrudden.service;

import com.komanrudden.model.entities.AccountEntity;
import com.komanrudden.model.repositories.AccountRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class AccountService extends BaseService<AccountEntity, AccountRepository> {

    @Inject
    public AccountService(AccountRepository accountRepository) {
        super(accountRepository);
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        return super.delete(id);
    }
}
