package br.com.challenge.transaction.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TransactionRequest {

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("operation_type_id")
    private Long operationTypeId;

    private BigDecimal amount;
}
