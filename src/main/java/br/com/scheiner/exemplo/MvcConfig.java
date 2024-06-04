package br.com.scheiner.exemplo;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import br.com.scheiner.exemplo.filter4.CustomRequestMappingHandlerAdapter;

@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public MvcConfig() {
        super();
    }

//    @Override
//    public void addInterceptors(final InterceptorRegistry registry) {
//        registry.addInterceptor(new LoggerInterceptor());
//
//    }
    
    
    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        return new CustomRequestMappingHandlerAdapter();
    }
}