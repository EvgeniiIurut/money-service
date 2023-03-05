package com.example.moneyservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransferDto {
    private Long fromAccount;
    private Long toAccount;
    private BigDecimal amount;
}
