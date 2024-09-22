package com.komanrudden.model.repositories;

import com.komanrudden.model.entities.TransactionType;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TransactionTypeRepository implements PanacheRepository<TransactionType> {

    public TransactionType findByType(String type) {
        return find("type", type).firstResult();
    }

    @Transactional
    public void create(TransactionType transactionType) {
        persist(transactionType);
    }

    @Transactional
    public void update(TransactionType transactionType) {
        TransactionType entity = findById(transactionType.getId());
        if (entity != null) {
            entity.type = transactionType.type;
            persist(entity);
        }
    }

    @Transactional
    public void delete(Long id) {
        delete("id", id);
    }

}

