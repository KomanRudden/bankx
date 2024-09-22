package com.komanrudden.service;

import com.komanrudden.model.entities.Bank;
import com.komanrudden.model.repositories.BankRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class BankService extends BaseService<Bank, BankRepository> {

    @Inject
    public BankService(BankRepository bankRepository) {
        super(bankRepository);
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        return super.delete(id);
    }
}
