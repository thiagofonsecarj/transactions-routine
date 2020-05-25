package br.com.challenge.transaction.exception;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@Slf4j
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RequiredFieldsException extends RuntimeException {

    public RequiredFieldsException(String message) {
        super(message);
        log.error(message);
    }
}
