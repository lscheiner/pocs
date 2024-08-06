package br.com.scheiner.exemplo;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.scheiner.exemplo.filter.LoggerInterceptor;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new LoggerInterceptor());

    }
	
	@Bean
	@Profile("teste")
    public FilterRegistrationBean<ErrorHandlingFilter> loggingFilter() {
        FilterRegistrationBean<ErrorHandlingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ErrorHandlingFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
	

	
	
}