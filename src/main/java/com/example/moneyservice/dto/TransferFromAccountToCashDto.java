package com.example.moneyservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferFromAccountToCashDto {
    private Long fromAccount;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal amount;
}
