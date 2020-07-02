package br.com.challenge.transaction.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class TransactionResponse {

    @JsonProperty("transaction_id")
    private Long transactionId;

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("operation_type_id")
    private Long operationTypeId;

    @ApiModelProperty(value = "Amount")
    private BigDecimal amount;

    @JsonProperty("event_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Timestamp eventDate;
}
