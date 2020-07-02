package br.com.challenge.transaction.service;

import br.com.challenge.transaction.exception.EntityNotFoundException;
import br.com.challenge.transaction.exception.RequiredFieldsException;
import br.com.challenge.transaction.exception.UnexpectedValuesException;
import br.com.challenge.transaction.model.entity.OperationType;
import br.com.challenge.transaction.model.entity.Transaction;
import br.com.challenge.transaction.model.request.TransactionRequest;
import br.com.challenge.transaction.model.response.TransactionResponse;
import br.com.challenge.transaction.repository.AccountRepository;
import br.com.challenge.transaction.repository.OperationTypeRepository;
import br.com.challenge.transaction.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Slf4j
@Service
public class TransactionService {

    private TransactionRepository repository;
    private OperationTypeRepository operationTypeRepository;
    private AccountRepository accountRepository;

    @Autowired
    public TransactionService(TransactionRepository repository, OperationTypeRepository operationTypeRepository, AccountRepository accountRepository) {
        this.repository = repository;
        this.operationTypeRepository = operationTypeRepository;
        this.accountRepository = accountRepository;
    }

    public TransactionResponse saveTransaction(TransactionRequest request) {
        log.info("persisting transaction ");
        validateRequest(request);
        validateExistingValues(request);
        request = applyBusinessRule(request);

        log.debug("requested transaction: " + request);
        ModelMapper modelMapper = new ModelMapper();
        Transaction entity = modelMapper.map(request, Transaction.class);
        entity.setId(null); // vc sabia?
        entity.setEventDate(new Timestamp(System.currentTimeMillis()));
        entity = repository.save(entity);

        return modelMapper.map(entity, TransactionResponse.class);
    }

    private void validateRequest(TransactionRequest request) {
        final Long ZERO = Long.valueOf(0);

        if(request == null || request.getAccountId() == null
                || request.getOperationTypeId() == null || request.getAmount() == null){
            throw new RequiredFieldsException("account_id, operation_type_id, and amount fields are required!");
        }

        if (ZERO.equals(request.getAmount().longValue()) || ZERO.equals(request.getAccountId())
                || ZERO.equals(request.getOperationTypeId()) ) {
            throw new UnexpectedValuesException("account_id, operation_type_id, and amount fields must not be zero!");
        }
    }

    private void validateExistingValues(TransactionRequest request) {

        if(!accountRepository.existsById(request.getAccountId())) {
            throw new EntityNotFoundException("Transaction cancelled! There is no account with id: "+ request.getAccountId());
        }

        if(!operationTypeRepository.existsById(request.getOperationTypeId())) {
            throw new EntityNotFoundException("Transaction cancelled! There is no operation type with id: "+ request.getOperationTypeId());
        }
    }

    private TransactionRequest applyBusinessRule(TransactionRequest request) {
        Optional<OperationType> optOperation = operationTypeRepository.findById(request.getOperationTypeId());

        switch (optOperation.get().getName()) {
            case COMPRA_A_VISTA:
            case COMPRA_PARCELADA:
            case SAQUE:
                if (request.getAmount().signum() == 1) {
                    request.setAmount(request.getAmount().negate());
                }
                break;
            case PAGAMENTO:
                if (request.getAmount().signum() == -1) {
                    request.setAmount(request.getAmount().negate());
                }
                break;
        }

        return request;
    }
}
