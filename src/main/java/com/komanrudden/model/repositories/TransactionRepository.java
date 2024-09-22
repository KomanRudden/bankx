package com.komanrudden.model.repositories;

import com.komanrudden.model.entities.Transaction;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class TransactionRepository implements PanacheRepository<Transaction> {

    public List<Transaction> findByCustomerId(Long customerId) {
        return list("customer.id", customerId);
    }

    public List<Transaction> findByAccountId(Long accountId) {
        return list("account.id", accountId);
    }

    public List<Transaction> findByTransactionTypeId(Long transactionTypeId) {
        return list("transactionType.id", transactionTypeId);
    }

    @Transactional
    public void create(Transaction transaction) {
        persist(transaction);
    }

    @Transactional
    public void update(Transaction transaction) {
        Transaction entity = findById(transaction.getId());
        if (entity != null) {
            entity.setCustomerEntity(transaction.getCustomerEntity());
            entity.setAccount(transaction.getAccount());
            entity.setTransactionType(transaction.getTransactionType());
            entity.setAmount(transaction.getAmount());
            entity.setTransactionFee(transaction.getTransactionFee());
            entity.setTransactionDate(transaction.getTransactionDate());
            entity.setDescription(transaction.getDescription());
            persist(entity);
        }
    }

    @Transactional
    public void delete(Long id) {
        delete("id", id);
    }

}

