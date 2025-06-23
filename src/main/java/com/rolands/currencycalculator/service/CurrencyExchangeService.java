package com.rolands.currencycalculator.service;

import com.rolands.currencycalculator.dto.CurrencyExchangeRateDto;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public interface CurrencyExchangeService {
    CurrencyExchangeRateDto fetchEcbXmlRates(String from, String to, LocalDate date);
    ResponseEntity<Map<String, BigDecimal>> fetchAllEcbXmlRates(LocalDate date);
}
