package com.komanrudden.model.repositories;

import com.komanrudden.model.entities.Bank;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class BankRepository implements PanacheRepository<Bank> {

    public Bank findByName(String name) {
        return find("UPPER(name)", name).firstResult();
    }

    @Transactional
    public void create(Bank bank) {
        persist(bank);
    }

    @Transactional
    public void update(Bank bank) {
        Bank entity = findById(bank.getId());
        if (entity != null) {
            entity.setName(bank.getName());
            entity.setUpdatedAt(bank.getUpdatedAt());
            persist(entity);
        }
    }
}

