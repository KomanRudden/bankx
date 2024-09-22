package com.komanrudden.model.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "transaction")
public class Transaction extends PanacheEntityBase {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @SequenceGenerator(name = "transaction_id_seq", sequenceName = "transaction_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "transaction_id_seq")
    private long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customerEntity;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;

    @ManyToOne
    @JoinColumn(name = "transaction_type_id", nullable = false)
    private TransactionType transactionType;

    @Column(name = "amount", updatable = false, nullable = false)
    private BigDecimal amount;

    @Column(name = "transaction_fee", updatable = false, nullable = false)
    private BigDecimal transactionFee;

    @Column(name = "transaction_date", updatable = false, nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "description", updatable = false, nullable = false)
    private String description;
}

