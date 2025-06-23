package com.rolands.currencycalculator.service.impl;

import com.rolands.currencycalculator.dto.CurrencyConversionResponseDto;
import com.rolands.currencycalculator.service.CurrencyExchangeService;
import com.rolands.currencycalculator.utility.CurrencyCode;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyExchangeServiceImpl.class);

    private final RestTemplate restTemplate;

    public CurrencyExchangeServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public CurrencyConversionResponseDto fetchEcbXmlRates(String from, String to, LocalDate date) {

        // Format currencies in upper case
        from = from.toUpperCase();
        to = to.toUpperCase();

        // Check if provided currencies exists
        validateCurrency(from);
        validateCurrency(to);

        // Make request to www.bank.lv
        Elements currencies = makeRequest(date);

        // Save only relevant currencies
        Map<String, BigDecimal> exchangeRateMap= new HashMap<>();
        exchangeRateMap.put("EUR", new BigDecimal(1));
        for (Element currency : currencies) {
            String id = currency.selectFirst("ID").text();
            String rate = currency.selectFirst("Rate").text();
            if(id.equals(from) || id.equals(to)) {
                exchangeRateMap.put(id, new BigDecimal(rate));
            }
        }

        CurrencyConversionResponseDto currencyConversionResponseDto = new CurrencyConversionResponseDto();
        currencyConversionResponseDto.setFrom(from);
        currencyConversionResponseDto.setTo(to);
        currencyConversionResponseDto.setExchangeTo(exchangeRateMap.get(to));
        currencyConversionResponseDto.setExchangeFrom(exchangeRateMap.get(from));

        return currencyConversionResponseDto;
    }

    @Override
    public ResponseEntity<Map<String, BigDecimal>> fetchAllEcbXmlRates(LocalDate date) {

        // Make request to www.bank.lv
        Elements currencies = makeRequest(date);

        // Save all currencies and their rates in Map
        Map<String, BigDecimal> exchangeRateMap= new HashMap<>();
        exchangeRateMap.put("EUR", new BigDecimal(1));
        for (Element currency : currencies) {
            String id = currency.selectFirst("ID").text();
            String rate = currency.selectFirst("Rate").text();
            exchangeRateMap.put(id, new BigDecimal(rate));
        }

        return ResponseEntity.ok(exchangeRateMap);
    }

    private Elements makeRequest(LocalDate date) {
        // Check if date is in correct boundaries
        validateDate(date);

        // Date formating for request
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = date.format(dateTimeFormatter);

        // Make request
        String baseUri = "https://www.bank.lv/vk/ecb.xml?date=";
        String currencyXmlString = restTemplate.getForObject(baseUri + formattedDate, String.class);

        Document doc = Jsoup.parse(currencyXmlString, "", Parser.xmlParser());
        Elements currencies = doc.select("Currency");

        return  currencies;
    }

    /*  Created: Rolands Bidzans
        Description: Function that validates Date
     */
    private void validateDate(LocalDate date){
        // https://www.bank.lv/vk/ecb.xml?date=19990201 -> first available date
        if (date.isBefore(LocalDate.of(1999, 1, 1))) {
            throw new IllegalArgumentException("Date must be after year 1999");
        } else if(date.isAfter(LocalDate.now()) ){
            throw new IllegalArgumentException("Date must be before " + LocalDate.now());
        }
    }

    /*  Created: Rolands Bidzans
        Description: Function that checks if currency exists
    */
    private void validateCurrency(String currency){
        try {
            CurrencyCode.valueOf(currency);
        }catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid currency provided", e);
        }
    }
}
