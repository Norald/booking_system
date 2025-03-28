package com.example.booking.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Booking System API", version = "1.0", description = "API documentation for the Booking System")
)
public class SwaggerConfig {
}