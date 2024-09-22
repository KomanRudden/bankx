package com.komanrudden.service;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
public abstract class BaseService<E extends PanacheEntityBase, R extends PanacheRepository<E>> implements AbstractService<E, R> {

    protected final R repository;

    public BaseService(R repository) {
        this.repository = repository;
    }

    @Override
    public R getRepository() {
        return repository;
    }
}