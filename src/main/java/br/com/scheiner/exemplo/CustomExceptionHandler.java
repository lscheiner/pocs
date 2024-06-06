package br.com.scheiner.exemplo;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Order(Ordered.HIGHEST_PRECEDENCE) 
@ControllerAdvice
public class CustomExceptionHandler  {

	
   @ExceptionHandler(value = RuntimeException.class)
    protected ResponseEntity<Object> runtimeException( RuntimeException ex, HttpStatus status, WebRequest request) {

        List<String> errors = Arrays.asList(ex.getMessage());

        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(status, errors);

        return new ResponseEntity<>(apiErrorMessage, apiErrorMessage.getStatus());
    }
    
}