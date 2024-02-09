package com.techgee.electronicvoting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:sql.properties")
public class ElectronicVotingConfig {

	 public ElectronicVotingConfig() {
		 super();
	 }
	 
	 @Bean
	 public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		 return new PropertySourcesPlaceholderConfigurer();
	 }
}
