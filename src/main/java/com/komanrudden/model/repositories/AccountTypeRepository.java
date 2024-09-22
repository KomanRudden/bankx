package com.komanrudden.model.repositories;

import com.komanrudden.model.entities.AccountType;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class AccountTypeRepository implements PanacheRepository<AccountType> {

    public AccountType findByType(String type) {
        return find("type", type).firstResult();
    }

    public List<AccountType> findByTypes(List<String> types) {
        return list("type in ?1", types);
    }

    @Transactional
    public void create(AccountType accountType) {
        persist(accountType);
    }

    @Transactional
    public void update(AccountType accountType) {
        AccountType entity = findById(accountType.getId());
        if (entity != null) {
            entity.type = accountType.type;
            entity.description = accountType.description;
            persist(entity);
        }
    }

    @Transactional
    public void delete(Long id) {
        delete("id", id);
    }

}

