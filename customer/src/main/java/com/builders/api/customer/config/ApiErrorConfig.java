package com.builders.api.customer.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class ApiErrorConfig {

	@Bean
	public MessageSource apiErrorMessageSource() {
		ReloadableResourceBundleMessageSource bundleMessageSource = new ReloadableResourceBundleMessageSource();
		bundleMessageSource.setBasename("classpath:/api_errors");
		bundleMessageSource.setDefaultEncoding("UTF-8");
		
		return bundleMessageSource;
	}
}
