package br.com.challenge.transaction.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountRequest {

    @JsonProperty("document_number")
    private String documentNumber;
}
