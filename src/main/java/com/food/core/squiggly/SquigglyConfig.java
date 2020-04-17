package com.food.core.squiggly;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.web.RequestSquigglyContextProvider;
import com.github.bohnman.squiggly.web.SquigglyRequestFilter;

@Configuration
public class SquigglyConfig {

	@Bean
	public FilterRegistrationBean<SquigglyRequestFilter> squigglyRequestFilter(ObjectMapper mapper){
		Squiggly.init(mapper, new RequestSquigglyContextProvider("campos",null));
		List<String> urlsPermitidas = Arrays.asList("/pedidos/*","/restaurantes/*");
		FilterRegistrationBean<SquigglyRequestFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new SquigglyRequestFilter());
		registrationBean.setOrder(1);
		registrationBean.setUrlPatterns(urlsPermitidas);
		return registrationBean;
	}
	
	
}
