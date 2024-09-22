package com.komanrudden.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account")
public class AccountEntity extends BaseEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @SequenceGenerator(name = "account_id_seq", sequenceName = "account_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "account_id_seq")
    private long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customerEntity;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "account_type_id", nullable = false)
    private AccountType accountType;

    @Column(name = "balance", updatable = false, nullable = false)
    private BigDecimal balance;
}

