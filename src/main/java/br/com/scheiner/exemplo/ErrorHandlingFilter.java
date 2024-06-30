package br.com.scheiner.exemplo;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(filterName = "ErrorHandlingFilter", urlPatterns = "/*")
public class ErrorHandlingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        try {
            filterChain.doFilter(request, response);
            
            System.out.println("depois");
        } catch (Throwable ex) {

        	System.out.println("erro");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    	
    	System.out.println("erro");

        // Configuração do filtro, se necessário
    }

    @Override
    public void destroy() {
    	
    	System.out.println("erro");

        // Limpeza do filtro, se necessário
    }
}
