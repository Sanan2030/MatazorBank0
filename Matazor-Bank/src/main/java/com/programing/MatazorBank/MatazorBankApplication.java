package com.programing.MatazorBank;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "MatazorBank App",
				description = "Backend Rest APIs for MatazorBank",
				version = "v1.0",
				contact = @Contact(
						name="Sanan Nabizada",
						email = "officialsanan0@gmail.com",
						url=""
				),
				license = @License(
						name="Sanan2030",
						url = ""
				)
		),
				externalDocs = @ExternalDocumentation(
						description = "MatazorBank App Documentation",
						url = ""

		)

)
public class MatazorBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatazorBankApplication.class, args);
	}

}
