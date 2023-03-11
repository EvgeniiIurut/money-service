package com.example.moneyservice.controller;

import com.example.moneyservice.dto.TransferDto;
import com.example.moneyservice.dto.TransferFromAccountToCashDto;
import com.example.moneyservice.dto.TransferFromCashToAccountDto;
import com.example.moneyservice.model.Transfer;
import com.example.moneyservice.service.impl.TransferServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransferController {
    private final TransferServiceImpl transferServiceImpl;

    @PostMapping("/transfer")
    public ResponseEntity<String> createTransferBetweenAccounts(@RequestBody TransferDto transferDto) {
        transferServiceImpl.createTransfer(transferDto.getFromAccount(), transferDto.getToAccount(), transferDto.getAmount());
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/take")
    public ResponseEntity<Transfer> createTransferFromAccountToCash(@RequestBody TransferFromAccountToCashDto transferFromAccountToCashDto) {
        Transfer transferFromAccountToCash = transferServiceImpl
                .createTransferFromAccountToCash(transferFromAccountToCashDto
                        .getFromAccount(), transferFromAccountToCashDto
                        .getAmount());
        return ResponseEntity.ok(transferFromAccountToCash);
    }

    @PostMapping("/put")
    public ResponseEntity<String> createTransferFromCashToAccount(@RequestBody TransferFromCashToAccountDto transferFromCashToAccountDto) {
        transferServiceImpl.createTransferFromCashToAccount(transferFromCashToAccountDto.getToAccount(), transferFromCashToAccountDto.getAmount());
        return ResponseEntity.ok("OK");
    }
}
