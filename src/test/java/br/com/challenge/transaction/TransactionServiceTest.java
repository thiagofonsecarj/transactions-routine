package br.com.challenge.transaction;

import br.com.challenge.transaction.exception.EntityNotFoundException;
import br.com.challenge.transaction.exception.RequiredFieldsException;
import br.com.challenge.transaction.exception.UnexpectedValuesException;
import br.com.challenge.transaction.model.request.TransactionRequest;
import br.com.challenge.transaction.model.response.TransactionResponse;
import br.com.challenge.transaction.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("tests")
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Test
    public void shouldCreateTransaction() {
        Long requestedAccountId = Long.valueOf(1l);
        Long requestedOperationType = Long.valueOf(1l);
        BigDecimal requestedAmount = BigDecimal.valueOf(23.45);
        TransactionRequest request = createRequest(requestedAccountId, requestedOperationType, requestedAmount);

        TransactionResponse response = transactionService.saveTransaction(request);

        assertNotNull(response);
        assertNotNull(response.getTransactionId());
        assertNotNull(response.getAccountId());
        assertEquals(requestedAccountId, response.getAccountId());
        assertNotNull(response.getOperationTypeId());
        assertEquals(requestedOperationType, response.getOperationTypeId());
        assertNotNull(response.getAmount());
        assertEquals(requestedAmount.negate(), response.getAmount());
        assertNotNull(response.getEventDate());
    }

    @Test(expected = RequiredFieldsException.class)
    public void shouldNotCreateTransactionWithEmptyForm() {
        transactionService.saveTransaction(null);
    }

    @Test(expected = UnexpectedValuesException.class)
    public void shouldNotCreateTransactionWithZeroValues() {
        Long zero = Long.valueOf(0l);
        TransactionRequest request = createRequest(zero, zero, BigDecimal.valueOf(zero));

        transactionService.saveTransaction(request);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldNotCreateTransactionWithInvalidAccount() {
        TransactionRequest request = createRequest(10l, 1l, BigDecimal.valueOf(23.45));

        transactionService.saveTransaction(request);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldNotCreateTransactionWithInvalidOperationType() {
        TransactionRequest request = createRequest(1l, 10l, BigDecimal.valueOf(23.45));

        transactionService.saveTransaction(request);
    }

    private TransactionRequest createRequest(Long requestedAccountId, Long requestedOperationType, BigDecimal requestedAmount) {
        TransactionRequest request = new TransactionRequest();
        request.setAccountId(requestedAccountId);
        request.setOperationTypeId(requestedOperationType);
        request.setAmount(requestedAmount);

        return request;
    }
}
