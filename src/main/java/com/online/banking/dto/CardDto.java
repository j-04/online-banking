package com.online.banking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Schema(
        description = "<u>Сущность Банковская карта</u>"
)
public class CardDto {
    private Long id;
    private String number;
    private String securityCode;
    private OffsetDateTime timestamp;
    private OffsetDateTime availableTill;
}
