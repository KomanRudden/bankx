package com.komanrudden.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "customer")
public class CustomerEntity extends BaseEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @SequenceGenerator(name = "customer_id_seq", sequenceName = "customer_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "customer_id_seq")
    private long id;

    @Column(name = "name", updatable = false, nullable = false)
    private String name;

    @Column(name = "email", updatable = false, nullable = false)
    private String email;

    @Column(name = "phone_number", updatable = false, nullable = false)
    private String phoneNumber;
}

