package com.komanrudden.service;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.validation.Valid;

import java.util.List;

public interface AbstractService<E extends PanacheEntityBase, R extends PanacheRepository<E>> {

    R getRepository();

    default List<E> getAll() {
        return getRepository().listAll();
    }

    default E getById(long id) {
        return getRepository().findById(id);
    }

    default E create(@Valid E entity) {
        getRepository().persist(entity);
        return entity;
    }

    default E update(long id, E entity) {
        E existingEntity = getRepository().findById(id);
        if (existingEntity != null) {
            // Update the existing entity with the new values
            // This would depend on the structure of your entity class
        }
        return existingEntity;
    }

    default boolean delete(long id) {
        return getRepository().deleteById(id);
    }
}