package br.com.scheiner.exemplo;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.scheiner.exemplo.filter3.LoggingAdvice;

@Configuration
public class Config  {

    
    @Bean
    public LoggingAdvice loggingAdvice() {
        return new LoggingAdvice();
    }
}