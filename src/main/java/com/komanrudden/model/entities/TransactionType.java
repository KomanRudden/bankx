package com.komanrudden.model.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "transaction_type")
public class TransactionType extends PanacheEntityBase {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @SequenceGenerator(name = "transaction_type_id_seq", sequenceName = "transaction_type_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "transaction_type_id_seq")
    private long id;

    @Column(name = "type", updatable = false, nullable = false)
    public String type;
}

