package com.example.moneyservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferFromCashToAccountDto {
    private Long toAccount;
    private BigDecimal amount;
}
