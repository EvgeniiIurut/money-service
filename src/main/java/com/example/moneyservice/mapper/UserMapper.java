package com.example.moneyservice.mapper;

import com.example.moneyservice.dto.TransferDto;
import com.example.moneyservice.dto.TransferFromAccountToCashDto;
import com.example.moneyservice.dto.TransferFromCashToAccountDto;
import com.example.moneyservice.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    Account mapTransferDtoToUser(TransferDto transferDto);

    Account mapTransferDtoToUser(TransferFromAccountToCashDto transferDto);

    Account mapTransferDtoToUser(TransferFromCashToAccountDto transferDto);
}
