package com.komanrudden.api;

import com.komanrudden.service.AbstractService;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

abstract class AbstractResource<T extends PanacheEntityBase, R extends PanacheRepository<T>> {

    AbstractService<T, R> service;

    public Response getAll() {
        return Response.ok(service.getAll()).build();
    }

    public Response get(@PathParam("id") long id) {
        T entity = service.getById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found").build();
        }
        return Response.ok(entity).build();
    }

    @Transactional
    public Response create(@Valid T entity) {
        T createdEntity = service.create(entity);
        return Response.status(Response.Status.CREATED).entity(createdEntity).build();
    }

    public Response update(@PathParam("id") long id, @Valid T entity) {
        T updatedEntity = service.update(id, entity);
        if (updatedEntity == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found").build();
        }
        return Response.ok(updatedEntity).build();
    }

    @Transactional
    public Response delete(@PathParam("id") long id) {
        boolean deleted = service.delete(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found").build();
        }
        return Response.noContent().build();
    }
}