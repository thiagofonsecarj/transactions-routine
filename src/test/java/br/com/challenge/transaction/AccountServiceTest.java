package br.com.challenge.transaction;

import br.com.challenge.transaction.exception.EntityNotFoundException;
import br.com.challenge.transaction.exception.ExistingEntityException;
import br.com.challenge.transaction.exception.RequiredFieldsException;
import br.com.challenge.transaction.model.request.AccountRequest;
import br.com.challenge.transaction.model.response.AccountResponse;
import br.com.challenge.transaction.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("tests")
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void shouldCreateAnAccount() {
        AccountRequest request = new AccountRequest();
        request.setDocumentNumber("32145678900");

        AccountResponse response = accountService.saveAccount(request);

        assertNotNull(response);
        assertNotNull(response.getAccountId());
        assertTrue(response.getAccountId() > 0);
        assertNotNull(response.getDocumentNumber());
        assertEquals(request.getDocumentNumber(), response.getDocumentNumber());
    }

    @Test(expected = RequiredFieldsException.class)
    public void shouldNotCreateAnAccountWithEmptyForm() {
        accountService.saveAccount(null);
    }

    @Test(expected = ExistingEntityException.class)
    public void shouldNotCreateAnAccountWithExistingDocumentNumber() {
        AccountRequest request = new AccountRequest();
        request.setDocumentNumber("12345678900");

        AccountResponse response = accountService.saveAccount(request);
    }

    @Test
    public void shouldGetAnAccount() {
        Long requestedAccountId = Long.valueOf(1);
        AccountResponse response = accountService.getAccount(requestedAccountId);

        assertNotNull(response);
        assertNotNull(response.getAccountId());
        assertTrue(response.getAccountId() > 0);
        assertEquals(response.getAccountId(), requestedAccountId);
        assertNotNull(response.getDocumentNumber());
    }

    @Test(expected = RequiredFieldsException.class)
    public void shouldNotGetAnAccountWithEmptyParam() {
        AccountResponse response = accountService.getAccount(null);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldNotGetAnAccountWithNonExistingId() {
        AccountResponse response = accountService.getAccount(10l);
    }
}
