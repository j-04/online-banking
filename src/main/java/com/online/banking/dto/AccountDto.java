package com.online.banking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Schema(
        description = "<u>Сущность Банковский счет</u>"
)
public class AccountDto {
    private Long id;
    private String number;
    private Integer money;
    private OffsetDateTime timestamp;
    private OffsetDateTime availableTill;
    private Boolean isBlocked;
    private Boolean isClosed;
}
