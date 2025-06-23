package com.rolands.currencycalculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Schema(description = "DTO representing a currency conversion rate between two currencies")
public class CurrencyExchangeRateDto{
    @Schema(description = "Source currency code (e.g., 'USD')", example = "USD")
    private String from;

    @Schema(description = "Target currency code (e.g., 'EUR')", example = "EUR")
    private String to;

    @Schema(description = "Exchange rate of the source currency", example = "1.0")
    private BigDecimal exchangeFrom;

    @Schema(description = "Exchange rate of the target currency", example = "0.92")
    private BigDecimal exchangeTo;
}
