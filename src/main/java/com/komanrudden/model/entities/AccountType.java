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
@Table(name = "account_type")
public class AccountType extends PanacheEntityBase {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @SequenceGenerator(name = "account_type_id_seq", sequenceName = "account_type_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "account_type_id_seq")
    private long id;

    @Column(name = "type", updatable = false, nullable = false)
    public String type;

    @Column(name = "description", updatable = false)
    public String description;
}

