package br.com.scheiner.exemplo;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.util.DisconnectedClientHelper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

@Component
public class ConnectionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
            request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
            
System.out.println(response);            
        } catch (Exception ex) {
        	
            DisconnectedClientHelper.isClientDisconnectedException(ex);
 
        }
    }

    // Outros métodos do Filter omitidos para simplicidade
}
