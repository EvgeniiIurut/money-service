package com.example.moneyservice.exception;

public class NegativeAmountException extends RuntimeException {
    public static final String MESSAGE = "Amount of account with id = (%s) should be positive";

    public NegativeAmountException(Long accountId) {
        super(String.format(MESSAGE, accountId));
    }
}
