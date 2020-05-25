package br.com.challenge.transaction.repository;

import br.com.challenge.transaction.model.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {}