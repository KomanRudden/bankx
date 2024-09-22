package com.komanrudden.model.repositories;

import com.komanrudden.model.entities.CustomerEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<CustomerEntity> {

    public CustomerEntity findByEmail(String email) {
        return find("email", email).firstResult();
    }

    public List<CustomerEntity> findByName(String name) {
        return list("name", name);
    }

    @Transactional
    public void create(CustomerEntity customerEntity) {
        persist(customerEntity);
    }

    @Transactional
    public void update(CustomerEntity customerEntity) {
        CustomerEntity entity = findById(customerEntity.getId());
        if (entity != null) {
            entity.setName(customerEntity.getName());
            entity.setEmail(customerEntity.getEmail());
            entity.setPhoneNumber(customerEntity.getPhoneNumber());
            entity.setUpdatedAt(customerEntity.getUpdatedAt());
            persist(entity);
        }
    }

    @Transactional
    public long delete(long id) {
        return delete("id", id);
    }

}

