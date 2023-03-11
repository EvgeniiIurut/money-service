package com.example.moneyservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferDto {
    private Long fromAccount;
    private Long toAccount;
    private BigDecimal amount;
}
