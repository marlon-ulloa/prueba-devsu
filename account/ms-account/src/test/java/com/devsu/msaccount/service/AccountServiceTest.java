package com.devsu.msaccount.service;

import com.devsu.msaccount.dto.AccountDTO;
import com.devsu.msaccount.dto.Response;
import com.devsu.msaccount.entity.Cuenta;
import com.devsu.msaccount.exception.ResourceNotFoundException;
import com.devsu.msaccount.repository.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    private AccountRepository accountRepository;
    private AccountService accountService;

    @BeforeEach
    public void setup(){
        accountRepository = Mockito.mock(AccountRepository.class);
        accountService =new AccountService(accountRepository);
    }

    @Test
    public void testCreateAccount(){
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setNumeroCuenta("200000");
        accountDTO.setTipoCuenta("Corriente");
        accountDTO.setClienteId(1L);
        Cuenta account = new Cuenta();
        account.setNumeroCuenta(accountDTO.getNumeroCuenta());
        account.setTipoCuenta(accountDTO.getTipoCuenta());
        account.setClienteId(accountDTO.getClienteId());
        when(accountRepository.save(any(Cuenta.class))).thenReturn(account);

        Response created = accountService.createCuenta(accountDTO);
        ObjectMapper objectMapper = new ObjectMapper();
        Cuenta accountCreated = objectMapper.convertValue(created.getData(), Cuenta.class);
        assertEquals("200000", accountCreated.getNumeroCuenta());

    }

    @Test
    public void testGetAccountByAccountNumber() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            accountService.getCuentaById(1L);
        });
    }



}