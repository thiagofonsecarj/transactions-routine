package br.com.challenge.transaction.service;

import br.com.challenge.transaction.exception.EntityNotFoundException;
import br.com.challenge.transaction.exception.ExistingEntityException;
import br.com.challenge.transaction.exception.RequiredFieldsException;
import br.com.challenge.transaction.model.entity.Account;
import br.com.challenge.transaction.model.request.AccountRequest;
import br.com.challenge.transaction.model.response.AccountResponse;
import br.com.challenge.transaction.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Slf4j
@Service
public class AccountService {

    private AccountRepository repository;

    @Autowired
    public AccountService(AccountRepository repository){
        this.repository = repository;
    }

    public AccountResponse getAccount(Long accountId) {
        log.info("obtaining account by id:" + accountId);

        validateAccountId(accountId);

        Optional<Account> optEntity = repository.findById(accountId);
        if(optEntity.isEmpty()){
            throw new EntityNotFoundException("Sorry, I did not find an account by id:" + accountId);
        }

        return new ModelMapper().map(optEntity.get(), AccountResponse.class);
    }

    private void validateAccountId(Long accountId) {
        if(accountId == null || Long.valueOf(0).equals(accountId)) {
            throw new RequiredFieldsException("Please, inform your account id!");
        }
    }

    public AccountResponse saveAccount(AccountRequest request) {
        log.info("persisting account ");
        validateRequest(request);
        validateExistingDocument(request.getDocumentNumber());

        log.debug("requested account:" + request);
        ModelMapper modelMapper = new ModelMapper();
        Account entity = modelMapper.map(request, Account.class);
        entity = repository.save(entity);

        return modelMapper.map(entity, AccountResponse.class);
    }

    private void validateRequest(AccountRequest request) {
        if(request == null || StringUtils.isEmpty(request.getDocumentNumber())){
            throw new RequiredFieldsException("Please, inform your document number!");
        }
    }

    private void validateExistingDocument(String documentNumber){
        Account entity = repository.findByDocumentNumber(documentNumber);
        if(entity != null && entity.getId() != null){
            throw new ExistingEntityException("This document number has been already associated with an account!");
        }
    }

}
