package com.fridgebuzz.parking.infrastructure.configuration;

import com.fridgebuzz.parking.application.service.RateService;
import com.fridgebuzz.parking.domain.repository.RateRepository;
import com.fridgebuzz.parking.domain.service.PriceService;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration
{
    @Bean
    RateService rateService(RateRepository rateRepository) {
        return new RateService(rateRepository);
    }

    @Bean
    PriceService priceService(RateRepository rateRepository) {
        return new PriceService(rateRepository);
    }

    @Bean
    public OpenAPI customOpenAPI() {
     return new OpenAPI()
            .info(new Info()
            .title("Parking API")
            .version("1.0")
            .description("This is an API for updating and querying parking rates.")
            );
    }
}
