package com.rolands.currencycalculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Schema(description = "Standard error response containing error type and message")
public class ErrorResponseDto {
    @Schema(description = "Short error code or type", example = "BadRequest")
    private String error;

    @Schema(description = "Detailed error message", example = "Invalid currency code provided")
    private String message;
}
