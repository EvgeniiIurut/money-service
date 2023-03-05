package com.example.moneyservice.exception;

public class SameAmountsException extends RuntimeException {
    public static final String MESSAGE = "Same accounts exception";

    public SameAmountsException(Long accountId) {
        super(String.format(MESSAGE, accountId));
    }
}
