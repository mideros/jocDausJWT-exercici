package com.mideros.diceGameJWT.configuration;

import java.util.TimeZone;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; //Adapter

import com.fasterxml.jackson.databind.ObjectMapper;

@ComponentScan(basePackages = { "com.mideros.diceGameJWT" })
@Configuration
@EnableWebMvc
public class WebMVCConfiguration implements WebMvcConfigurer { // extends WebMvcConfigurerAdapter

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/");
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setTimeZone(TimeZone.getDefault());
		return mapper;
	}
}