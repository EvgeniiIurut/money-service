package com.example.moneyservice.service;

import com.example.moneyservice.exception.NegativeAmountException;
import com.example.moneyservice.exception.SameAccountsException;
import com.example.moneyservice.model.Account;
import com.example.moneyservice.model.Transfer;
import com.example.moneyservice.repository.AccountRepository;
import com.example.moneyservice.repository.TransferRepository;
import com.example.moneyservice.service.impl.TransferServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransferServiceImplTest {

    private Account account;
    private static final Account transferToAccount = new Account(2L, BigDecimal.ZERO, 0);
    private static final Account accountWithOutMoneyByTransfer = new Account(1L, new BigDecimal(900), 0);
    private static final Account accountWithMoneyByTransfer = new Account(2L, new BigDecimal(100), 0);
    private static final Account accountWithOutMoney = new Account(1L, new BigDecimal(900), 0);
    private static final Account accountWithMoney = new Account(1L, new BigDecimal(1100), 0);
    private static final Account accountWithNotEnoughMoney = new Account(1L, BigDecimal.ZERO, 0);

    @Mock
    AccountRepository accountRepository;
    @Mock
    TransferRepository transferRepository;

    @InjectMocks
    TransferServiceImpl transferServiceImpl;

    @BeforeEach
    void initialize() {
        account = new Account(1L, new BigDecimal(1000), 0);
    }

    @Test
    void shouldMakeTransferFromAccountToOtherAccount() {
        when(accountRepository.getById(1L)).thenReturn(account);
        when(accountRepository.getById(2L)).thenReturn(transferToAccount);
        when(accountRepository.save(any())).thenReturn(accountWithOutMoneyByTransfer);
        when(accountRepository.save(any())).thenReturn(accountWithMoneyByTransfer);
        when(transferRepository.save(any())).thenReturn(new Transfer(accountWithOutMoneyByTransfer, accountWithMoneyByTransfer, new BigDecimal(100)));

        Transfer transferFromAccountToOtherAccount = transferServiceImpl.createTransfer(1L, 2L, new BigDecimal(100));

        assertEquals(accountWithOutMoneyByTransfer, transferFromAccountToOtherAccount.getFromAccount());
        assertEquals(accountWithMoneyByTransfer, transferFromAccountToOtherAccount.getToAccount());
        assertEquals(new BigDecimal(100), transferFromAccountToOtherAccount.getAmount());
        verify(accountRepository, times(2)).save(any());
        verify(accountRepository, times(2)).getById(any());
        verify(transferRepository, times(1)).save(any());
    }

    @Test
    void shouldMakeTransferFromAccountToCash() {
        when(accountRepository.getById(any())).thenReturn(account);
        when(accountRepository.save(any())).thenReturn(accountWithOutMoney);
        when(transferRepository.save(any())).thenReturn(new Transfer(account, null, new BigDecimal(100)));

        Transfer transferFromAccountToCash = transferServiceImpl.createTransferFromAccountToCash(1L, new BigDecimal(100));

        assertEquals(new BigDecimal(100), transferFromAccountToCash.getAmount());
        assertEquals(accountWithOutMoney, transferFromAccountToCash.getFromAccount());
        verify(accountRepository, times(1)).save(any());
        verify(accountRepository, times(1)).getById(any());
        verify(transferRepository, times(1)).save(any());
    }

    @Test
    void shouldMakeTransferFromCashToAccount() {
        when(accountRepository.getById(any())).thenReturn(account);
        when(accountRepository.save(any())).thenReturn(accountWithMoney);
        when(transferRepository.save(any())).thenReturn(new Transfer(account, null, new BigDecimal(100)));

        Transfer transferFromAccountToCash = transferServiceImpl.createTransferFromCashToAccount(1L, new BigDecimal(100));

        assertEquals(new BigDecimal(100), transferFromAccountToCash.getAmount());
        assertEquals(accountWithMoney, transferFromAccountToCash.getFromAccount());
        verify(accountRepository, times(1)).save(any());
        verify(accountRepository, times(1)).getById(any());
        verify(transferRepository, times(1)).save(any());
    }

    @Test
    void shouldThrowExceptionForNegativeBalance() {
        when(accountRepository.getById(any())).thenReturn(accountWithNotEnoughMoney);

        assertThrows(NegativeAmountException.class, () -> transferServiceImpl.createTransferFromAccountToCash(1L, new BigDecimal(100)));

        verify(accountRepository, times(1)).getById(any());
    }

    @Test
    void shouldThrowExceptionForSameAccountTransfer() {
        assertThrows(SameAccountsException.class, () -> transferServiceImpl.createTransfer(1L, 1L, new BigDecimal(100)));
    }
}