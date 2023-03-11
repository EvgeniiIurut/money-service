package com.example.moneyservice.exception;

public class SameAccountsException extends RuntimeException {
    public static final String MESSAGE = "Same accounts exception";

    public SameAccountsException(Long accountId) {
        super(String.format(MESSAGE, accountId));
    }
}
