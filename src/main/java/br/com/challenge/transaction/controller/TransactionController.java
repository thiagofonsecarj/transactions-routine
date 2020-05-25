package br.com.challenge.transaction.controller;

import br.com.challenge.transaction.model.request.TransactionRequest;
import br.com.challenge.transaction.model.response.TransactionResponse;
import br.com.challenge.transaction.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/transactions")
@Api(value = "Transactions")
public class TransactionController {

    private TransactionService service;

    @Autowired
    private TransactionController(TransactionService service){
        this.service = service;
    }

    @ApiOperation(value = "Create a new transaction", response = TransactionResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "New transaction created"),
            @ApiResponse(code = 400, message = "Some uninformed data is expected"),
            @ApiResponse(code = 409, message = "Account or Operation Type doesnt exist"),
            @ApiResponse(code = 500, message = "Abnormal behavior")
    })
    @PostMapping
    public ResponseEntity<TransactionResponse> saveTransaction(@RequestBody TransactionRequest request) {
        log.info("Saving transaction: " + request);

        return ResponseEntity.status(HttpStatus.OK).body(service.saveTransaction(request));
    }
}
