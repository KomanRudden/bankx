package com.komanrudden.api;

import com.komanrudden.model.entities.AccountEntity;
import com.komanrudden.model.entities.Bank;
import com.komanrudden.model.entities.CustomerEntity;
import com.komanrudden.service.AccountService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@Disabled
@QuarkusTest
public class AccountEntityResourceTest {

    @InjectMock
    AccountService accountService;

    @BeforeEach
    public void setup() {

        CustomerEntity mockCustomerEntity = new CustomerEntity();
        Bank mockBank = new Bank();
        AccountEntity mockAccount = new AccountEntity();

        Mockito.when(accountService.getAll()).thenReturn(Collections.singletonList(mockAccount));
        Mockito.when(accountService.getById(1L)).thenReturn(mockAccount);
        Mockito.when(accountService.create(any(AccountEntity.class))).thenReturn(mockAccount);
        Mockito.when(accountService.update(anyLong(), any(AccountEntity.class))).thenReturn(mockAccount);
        Mockito.when(accountService.delete(1L)).thenReturn(true);
    }

    @Test
    public void testGetAllAccounts() {
        given()
                .when().get("/accounts")
                .then()
                .statusCode(200)
                .body("[0].name", is("Test Account"));
    }

    @Test
    public void testGetAccount() {
        given()
                .when().get("/accounts/1")
                .then()
                .statusCode(200)
                .body("name", is("Test Account"));
    }

    @Test
    public void testGetAccountNotFound() {
        Mockito.when(accountService.getById(2L)).thenReturn(null);

        given()
                .when().get("/accounts/2")
                .then()
                .statusCode(404)
                .body(is("Account not found"));
    }

    @Test
    public void testCreateAccount() {
        AccountEntity newAccount = new AccountEntity();
//        newAccount.setName("New Account");
//        newAccount.setBalance(2000.0);

        given()
                .contentType(ContentType.JSON)
                .body(newAccount)
                .when().post("/accounts")
                .then()
                .statusCode(201)
                .body("name", is("Test Account"));
    }

    @Test
    public void testUpdateAccount() {
        AccountEntity updatedAccount = new AccountEntity();
//        updatedAccount.setName("Updated Account");
//        updatedAccount.setBalance(1500.0);

        given()
                .contentType(ContentType.JSON)
                .body(updatedAccount)
                .when().put("/accounts/1")
                .then()
                .statusCode(200)
                .body("name", is("Test Account"));
    }

    @Test
    public void testUpdateAccountNotFound() {
        AccountEntity updatedAccount = new AccountEntity();
//        updatedAccount.setName("Updated Account");
//        updatedAccount.setBalance(1500.0);

        Mockito.when(accountService.update(2L, updatedAccount)).thenReturn(null);

        given()
                .contentType(ContentType.JSON)
                .body(updatedAccount)
                .when().put("/accounts/2")
                .then()
                .statusCode(404)
                .body(is("Account not found"));
    }

    @Test
    public void testDeleteAccount() {
        given()
                .when().delete("/accounts/1")
                .then()
                .statusCode(204);
    }

    @Test
    public void testDeleteAccountNotFound() {
        Mockito.when(accountService.delete(2L)).thenReturn(false);

        given()
                .when().delete("/accounts/2")
                .then()
                .statusCode(404)
                .body(is("Account not found"));
    }
}
