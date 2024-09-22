package com.komanrudden.service;

import com.komanrudden.model.entities.Transaction;
import com.komanrudden.model.repositories.TransactionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class TransactionService extends BaseService<Transaction, TransactionRepository> {

    @Inject
    public TransactionService(TransactionRepository transactionRepository) {
        super(transactionRepository);
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        return super.delete(id);
    }
}
