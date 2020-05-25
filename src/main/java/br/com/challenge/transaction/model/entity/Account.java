package br.com.challenge.transaction.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "document_number")
    private String documentNumber;
}
