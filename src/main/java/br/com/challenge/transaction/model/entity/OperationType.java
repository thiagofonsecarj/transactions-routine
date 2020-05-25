package br.com.challenge.transaction.model.entity;

import br.com.challenge.transaction.model.enums.OperationTypeEnum;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "operation_type")
public class OperationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private OperationTypeEnum name;

    @Column(name = "description")
    private String description;
}
