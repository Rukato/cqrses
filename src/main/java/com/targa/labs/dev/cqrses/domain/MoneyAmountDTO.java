package com.targa.labs.dev.cqrses.domain;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MoneyAmountDTO {
    private BigDecimal amount;
}
