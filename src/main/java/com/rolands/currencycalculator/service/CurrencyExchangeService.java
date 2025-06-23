package com.rolands.currencycalculator.service;

import com.rolands.currencycalculator.dto.CurrencyConversionResponseDto;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public interface CurrencyExchangeService {
    CurrencyConversionResponseDto fetchEcbXmlRates(String from, String to, LocalDate date);
    ResponseEntity<Map<String, BigDecimal>> fetchAllEcbXmlRates(LocalDate date);
}
