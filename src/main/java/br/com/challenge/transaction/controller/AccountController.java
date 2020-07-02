package br.com.challenge.transaction.controller;

import br.com.challenge.transaction.model.request.AccountRequest;
import br.com.challenge.transaction.model.response.AccountResponse;
import br.com.challenge.transaction.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/accounts")
@Api(value = "Accounts")
public class AccountController {

    private AccountService service;

    @Autowired
    public AccountController(AccountService service){
        this.service = service;
    }

    @ApiOperation(value = "Create a new account", response = AccountResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "New account created"),
            @ApiResponse(code = 400, message = "Some uninformed data is expected"),
            @ApiResponse(code = 409, message = "Document number has been already associated with another account"),
            @ApiResponse(code = 500, message = "Abnormal behavior")
    })
    @PostMapping
    public ResponseEntity<AccountResponse> saveAccount(@RequestBody AccountRequest request) {
        log.info("Saving account ");

        return ResponseEntity.status(HttpStatus.OK).body(service.saveAccount(request));
    }

    @ApiOperation(value = "Get account information by its id", response = AccountResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Account information obtained"),
            @ApiResponse(code = 400, message = "Some uninformed data is expected"),
            @ApiResponse(code = 500, message = "Abnormal behavior")
    })
    @GetMapping(path = "/{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable(value = "id", required = true) Long accountId) {
        log.info("Getting account " + accountId);

        return ResponseEntity.status(HttpStatus.OK).body(service.getAccount(accountId));
    }
}
