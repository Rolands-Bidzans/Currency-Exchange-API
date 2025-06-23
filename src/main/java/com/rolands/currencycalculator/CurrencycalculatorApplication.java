package com.rolands.currencycalculator;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Currency Exchange API",
				version = "v1",
				contact = @Contact(
						name = "Rolands Bidzāns",
						email = "rolandsnorigas@gmail.com"
				),
				license=@License(
						name = "No License"
				),
				description = """
					This API is used to retrieve currency exchange rates from https://www.bank.lv/vk/ecb.xml
				"""
		)
)
public class CurrencycalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencycalculatorApplication.class, args);
	}

}
