package com.example.moneyservice.controller;

import com.example.moneyservice.dto.TransferDto;
import com.example.moneyservice.dto.TransferFromAccountToCashDto;
import com.example.moneyservice.dto.TransferFromCashToAccountDto;
import com.example.moneyservice.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;

    @PatchMapping("/transfer")
    public ResponseEntity<String> createTransferBetweenAccounts(@RequestBody TransferDto transferDto) {
        transferService.createTransfer(transferDto.getFromAccount(), transferDto.getToAccount(), transferDto.getAmount());
        return ResponseEntity.ok("OK");
    }

    @PatchMapping("/take")
    public ResponseEntity<String> createTransferFromAccountToCash(@RequestBody TransferFromAccountToCashDto transferFromAccountToCashDto) {
        transferService.createTransferFromAccountToCash(transferFromAccountToCashDto.getFromAccount(), transferFromAccountToCashDto.getAmount());
        return ResponseEntity.ok("OK");
    }

    @PatchMapping("/put")
    public ResponseEntity<String> createTransferFromCashToAccount(@RequestBody TransferFromCashToAccountDto transferFromCashToAccountDto) {
        transferService.createTransferFromCashToAccount(transferFromCashToAccountDto.getToAccount(), transferFromCashToAccountDto.getAmount());
        return ResponseEntity.ok("OK");
    }
}
