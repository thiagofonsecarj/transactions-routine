package br.com.challenge.transaction.repository;

import br.com.challenge.transaction.model.entity.OperationType;
import org.springframework.data.repository.CrudRepository;

public interface OperationTypeRepository extends CrudRepository<OperationType, Long> {}
