package com.komanrudden.model.repositories;

import com.komanrudden.model.entities.Notification;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class NotificationRepository implements PanacheRepository<Notification> {

    public List<Notification> findByCustomerId(Long customerId) {
        return list("customer.id", customerId);
    }

    public List<Notification> findByTransactionId(Long transactionId) {
        return list("transaction.id", transactionId);
    }

    @Transactional
    public void create(Notification notification) {
        persist(notification);
    }

    @Transactional
    public void update(Notification notification) {
        Notification entity = findById(notification.getId());
        if (entity != null) {
            entity.setCustomer(notification.getCustomer());
            entity.setTransaction(notification.getTransaction());
            entity.setMessage(notification.getMessage());
            entity.setUpdatedAt(notification.getUpdatedAt());
            persist(entity);
        }
    }

    @Transactional
    public void delete(Long id) {
        delete("id", id);
    }

}

