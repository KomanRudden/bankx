package com.komanrudden.api;

import com.komanrudden.model.entities.Bank;
import com.komanrudden.service.BankService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@QuarkusTest
class BankResourceTest {

    private BankService bankService;
    private BankResource bankResource;

    @BeforeEach
    void setUp() {
        bankService = Mockito.mock(BankService.class);
        bankResource = new BankResource(bankService);
    }

    @Test
    @DisplayName("Should return all banks")
    void shouldReturnAllBanks() {
        when(bankService.getAll()).thenReturn(Collections.singletonList(new Bank()));
        Response response = bankResource.getAllBanks();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    @DisplayName("Should return a bank by ID")
    void shouldReturnBankById() {
        when(bankService.getById(anyLong())).thenReturn(new Bank());
        Response response = bankResource.getBank(1L);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    @DisplayName("Should create a new bank")
    void shouldCreateNewBank() {
        when(bankService.create(any(Bank.class))).thenReturn(new Bank());
        Response response = bankResource.createBank(new Bank());
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    @DisplayName("Should update a bank")
    void shouldUpdateBank() {
        when(bankService.update(anyLong(), any(Bank.class))).thenReturn(new Bank());
        Response response = bankResource.updateBank(1L, new Bank());
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    @DisplayName("Should not delete a bank that does not exist")
    void shouldDeleteBank() {
        Response response = bankResource.deleteBank(1L);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
}