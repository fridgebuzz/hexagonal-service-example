package com.spothero.parking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ParkingRateApplication
{
	public static void main(String[] args) throws IOException
    {
        SpringApplication.run(ParkingRateApplication.class, args);
	}
}
