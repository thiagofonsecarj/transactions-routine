package br.com.challenge.transaction.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "operation_type_id")
    private OperationType operationType;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "event_date")
    private Timestamp eventDate;
}
