package com.online.banking.mapper;

import com.online.banking.dto.CardDto;
import com.online.banking.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface CardMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "number", ignore = true),
            @Mapping(target = "timestamp", ignore = true),
            @Mapping(target = "availableTill", ignore = true),
            @Mapping(target = "closed", ignore = true),
            @Mapping(target = "blocked", ignore = true)
    })
    Card toCard(CardDto cardDto);

    CardDto toCardDto(Card card);
}
