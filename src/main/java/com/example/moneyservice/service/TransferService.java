package com.example.moneyservice.service;

import com.example.moneyservice.model.Transfer;

import java.math.BigDecimal;

public interface TransferService {
    Transfer createTransfer(Long fromAccountNumber, Long toAccountNumber, BigDecimal amount);

    Transfer createTransferFromAccountToCash(Long fromAccountNumber, BigDecimal amount);

    Transfer createTransferFromCashToAccount(Long toAccountNumber, BigDecimal amount);
}
