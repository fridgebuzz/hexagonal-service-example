package com.spothero.parking.infrastructure.configuration;

import com.spothero.parking.application.service.RateService;
import com.spothero.parking.domain.repository.RateRepository;
import com.spothero.parking.domain.service.PriceService;
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
}
