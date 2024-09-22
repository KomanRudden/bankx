package com.komanrudden.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "notification")
public class Notification extends BaseEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @SequenceGenerator(name = "notification_id_seq", sequenceName = "notification_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "notification_id_seq")
    private long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @Column(name = "message", updatable = false, nullable = false)
    private String message;
}

