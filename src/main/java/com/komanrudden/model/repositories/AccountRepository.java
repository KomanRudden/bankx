package com.komanrudden.model.repositories;

import com.komanrudden.model.entities.AccountEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class AccountRepository implements PanacheRepository<AccountEntity> {

    public List<AccountEntity> findByCustomerId(Long customerId) {
        return list("customer.id", customerId);
    }

    public List<AccountEntity> findByBankId(Long bankId) {
        return list("bank.id", bankId);
    }

    public List<AccountEntity> findByAccountTypeId(Long accountTypeId) {
        return list("accountType.id", accountTypeId);
    }

    @Transactional
    public void create(AccountEntity account) {
        persist(account);
    }

    @Transactional
    public void update(AccountEntity account) {
        AccountEntity entity = findById(account.getId());
        if (entity != null) {
            entity.setCustomerEntity(account.getCustomerEntity());
            entity.setBank(account.getBank());
            entity.setAccountType(account.getAccountType());
            entity.setBalance(account.getBalance());
            entity.setUpdatedAt(account.getUpdatedAt());
            persist(entity);
        }
    }

    @Transactional
    public void delete(Long id) {
        delete("id", id);
    }
}

