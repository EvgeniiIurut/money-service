package com.example.moneyservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferFromAccountToCashDto {
    private Long fromAccount;
    private BigDecimal amount;
}
