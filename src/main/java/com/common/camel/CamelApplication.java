package com.common.camel;

import lombok.extern.log4j.Log4j2;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.spi.RestConfiguration;
import org.apache.camel.spi.RestConsumerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.TimeZone;

@Log4j2
@SpringBootApplication
@EnableScheduling
public class CamelApplication {

	@Autowired
	private Environment env;

	@PostConstruct
	public void init() {
		// Установить для приложения таймзону по дефолту
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Moscow"));
	}

	public static void main(String[] args) {
		SpringApplication.run(CamelApplication.class, args);
	}

}
