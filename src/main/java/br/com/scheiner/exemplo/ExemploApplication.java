package br.com.scheiner.exemplo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import feign.Request;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.observation.DefaultMeterObservationHandler;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.ObservationTextPublisher;

@EnableFeignClients
@SpringBootApplication(scanBasePackageClasses = ExemploApplication.class)
public class ExemploApplication {

	public static void main(String[] args) {
		
	       final var observationRegistry = ObservationRegistry.create();
	        // create meter registry and observation handler
	        final var meterRegistry = new SimpleMeterRegistry();
	        final var meterObservationHandler = new DefaultMeterObservationHandler(meterRegistry);
	        // create simple logging observation handler
	        final var loggingObservationHandler = new ObservationTextPublisher(System.out::println);
	        // register observation handlers
	        observationRegistry
	          .observationConfig()
	          .observationHandler(meterObservationHandler)
	          .observationHandler(loggingObservationHandler);
		
		SpringApplication.run(ExemploApplication.class, args);
	}

	
	@Bean
	MeterRegistryCustomizer<MeterRegistry> configurer(
	    @Value("${spring.application.name}") String applicationName) {
	    return (registry) -> registry.config().commonTags("application", applicationName);
	}
	
	@Bean
    public Request.Options requestOptions() {
        return new Request.Options(1, 1);
    }
	
//	@Bean
//	public Filter loggingFilter(){
//		
//	    return new AbstractRequestLoggingFilter() {
//
//	        @Override
//	        protected void beforeRequest(HttpServletRequest request, String message) {
//	            System.out.println("beforeRequest: " +message);
//	        }
//
//	        @Override
//	        protected void afterRequest(HttpServletRequest request, String message) {
//	        	System.out.println("cliente desconectou");
//	        }
//	    };
//	}
}
