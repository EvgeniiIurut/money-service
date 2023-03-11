package com.example.moneyservice.service.impl;

import com.example.moneyservice.exception.NegativeAmountException;
import com.example.moneyservice.exception.SameAccountsException;
import com.example.moneyservice.model.Account;
import com.example.moneyservice.model.Transfer;
import com.example.moneyservice.repository.AccountRepository;
import com.example.moneyservice.repository.TransferRepository;
import com.example.moneyservice.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public Transfer createTransfer(Long fromAccountNumber, Long toAccountNumber, BigDecimal amount) {

        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new SameAccountsException(fromAccountNumber);
        }
        Account fromAccount = accountRepository.getById(fromAccountNumber);
        Account toAccount = accountRepository.getById(toAccountNumber);
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new NegativeAmountException(fromAccountNumber);
        }
        fromAccount.reduceBalance(amount);
        toAccount.increaseBalance(amount);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        Transfer currentTransfer = transferRepository.save(new Transfer(fromAccount, toAccount, amount));
        log.info("Transfer from account with id = ({}) to account with id = ({}) successfully done", fromAccountNumber, toAccountNumber);
        return currentTransfer;
    }

    @Transactional
    public Transfer createTransferFromAccountToCash(Long fromAccountNumber, BigDecimal amount) {
        Account fromAccount = accountRepository.getById(fromAccountNumber);
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new NegativeAmountException(fromAccountNumber);
        }
        fromAccount.reduceBalance(amount);
        accountRepository.save(fromAccount);
        Transfer currentTransfer = transferRepository.save(new Transfer(fromAccount, null, amount));
        log.info("Transfer from account with id = ({}) to CASH successfully done", fromAccountNumber);
        return currentTransfer;
    }

    @Transactional
    public Transfer createTransferFromCashToAccount(Long toAccountNumber, BigDecimal amount) {
        Account toAccount = accountRepository.getById(toAccountNumber);
        toAccount.increaseBalance(amount);
        accountRepository.save(toAccount);
        Transfer currentTransfer = transferRepository.save(new Transfer(toAccount, null, amount));
        log.info("Transfer CASH to account with id = ({}) successfully done", toAccountNumber);
        return currentTransfer;
    }
}
