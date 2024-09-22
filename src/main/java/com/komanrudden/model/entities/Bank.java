package com.komanrudden.model.entities;

import com.komanrudden.model.annotations.ValidBankName;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "bank", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class Bank extends PanacheEntityBase {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @SequenceGenerator(name = "bank_id_seq", sequenceName = "bank_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "bank_id_seq")
    private long id;

    @ValidBankName
    @Column(name = "name", updatable = false, nullable = false)
    private String name;

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", updatable = false, nullable = false)
    private LocalDateTime updatedAt;
}

