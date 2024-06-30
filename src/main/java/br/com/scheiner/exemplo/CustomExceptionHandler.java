package br.com.scheiner.exemplo;

import java.io.EOFException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.catalina.connector.ClientAbortException;
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

	
	@ExceptionHandler(IOException.class)
    public void handleClientAbortException(IOException ex) {
        System.out.println("Cliente desconectado inesperadamente: " + ex.getMessage());
    }
	
	 @ExceptionHandler(ClientAbortException.class)
	    public void handleClientAbortException(ClientAbortException ex) {
	        System.out.println("Cliente desconectado inesperadamente: " + ex.getMessage());
	    }
	 
	 @ExceptionHandler(EOFException.class)
	    public void handleClientAbortException(EOFException  ex) {
	        System.out.println("Cliente desconectado inesperadamente: " + ex.getMessage());
	    }
	
	
   @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ApiErrorMessage> runtimeException( RuntimeException ex, WebRequest request ) {

        List<String> errors = Arrays.asList(ex.getMessage());

        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, errors);

        return new ResponseEntity<>(apiErrorMessage, apiErrorMessage.getStatus());
    }
    
}