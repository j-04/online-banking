package com.online.banking.mapper;

import com.online.banking.dto.AccountDto;
import com.online.banking.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(uses = {
        CardMapper.class
})
public interface AccountMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "number", ignore = true),
            @Mapping(target = "timestamp", ignore = true),
            @Mapping(target = "availableTill", ignore = true),
            @Mapping(target = "closed", ignore = true),
            @Mapping(target = "blocked", ignore = true)
    })
    Account toAccount(AccountDto accountDto);

    AccountDto toAccountDto(Account account);
}
