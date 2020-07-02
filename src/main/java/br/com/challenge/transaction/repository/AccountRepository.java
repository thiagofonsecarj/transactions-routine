package br.com.challenge.transaction.repository;

import br.com.challenge.transaction.model.entity.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Account findByDocumentNumber(String documentNumber);
}
