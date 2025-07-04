package com.rolands.currencycalculator.controller;

import com.rolands.currencycalculator.dto.CurrencyExchangeRateDto;
import com.rolands.currencycalculator.dto.ErrorResponseDto;
import com.rolands.currencycalculator.service.CurrencyExchangeService;
import com.rolands.currencycalculator.utility.CurrencyCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Tag(
        name = "Currency Exchange API",
        description = "Provides endpoints for retrieving currency exchange rates."
)
public class CurrencyExchangeController {

    private final CurrencyExchangeService service;

    public CurrencyExchangeController(CurrencyExchangeService service) {
        this.service = service;
    }

    @Operation(
            summary = "Retrieve exchange rates for specific currencies on a given date",
            description = "Returns exchange rates between the specified source and target currencies for a given date.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful response with exchange rate data",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CurrencyExchangeRateDto.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid parameter provided",
                            content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponseDto.class),
                                examples = @ExampleObject(value = """
                                            {
                                                "error": "Invalid input",
                                                "message": "Invalid currency provided"
                                            }
                                """)
                            )
                    )
            }
    )
    @GetMapping("/exchange-rate")
    public CurrencyExchangeRateDto getExchangeRate(@RequestParam String from,
                                                 @RequestParam String to,
                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return service.fetchEcbXmlRates(from, to, date);
    }

    @Operation(
            summary = "Get supported currency codes",
            description = "Returns a list of all available currency codes supported by the API.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "A list of supported currency codes",
                            content = @Content(
                                mediaType = "application/json",
                                examples = @ExampleObject(value = """
                                            [
                                               "EUR",
                                               "AUD",
                                               "BGN",
                                               "BRL",
                                               "CAD",
                                               "CHF",
                                               "CNY"
                                            ]
                                """)
                            )
                    )
            }
    )
    @GetMapping("/currencies")
    public ResponseEntity<List<String>> getSupportedCurrencies() {
        List<String> currencies = Arrays.stream(CurrencyCode.values())
                .map(Enum::name)
                .toList();
        return ResponseEntity.ok(currencies);
    }

    @Operation(
            summary = "Get all exchange rates for a specific date",
            description = "Returns a map of currency codes and their corresponding exchange rates for the specified date.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "A map of currency codes to their exchange rates",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = """
                                            {
                                                "CHF": 0.93870,
                                                "MXN": 22.08620,
                                                "ZAR": 20.77290,
                                                "INR": 99.53500,
                                                "CNY": 8.24420,
                                                "THB": 37.81200,
                                                "AUD": 1.79630,
                                                "ILS": 3.98610,
                                                "KRW": 1592.49000,
                                                "JPY": 169.27000
                                            }
                                """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid parameter provided",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class),
                                    examples = @ExampleObject(value = """
                                        {
                                            "error": "Invalid input",
                                            "message": "Invalid currency provided"
                                        }
                                    """)
                            )
                    )
            }
    )
    @GetMapping("/all-exchange-rate")
    public ResponseEntity<Map<String, BigDecimal>> getAllExchangeRates(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return service.fetchAllEcbXmlRates(date);
    }
}
