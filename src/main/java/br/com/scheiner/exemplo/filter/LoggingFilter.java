package br.com.scheiner.exemplo.filter;

import java.io.IOException;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(1)
public class LoggingFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

       ContentCachingResponseWrapper responseCacheWrapperObject = new ContentCachingResponseWrapper((HttpServletResponse) res);
	   HttpServletRequest request = (HttpServletRequest) req;
	   CachedHttpServletRequestWrapper wrappedRequest = new CachedHttpServletRequestWrapper(request);
	   chain.doFilter(wrappedRequest, responseCacheWrapperObject);
	   responseCacheWrapperObject.copyBodyToResponse();
	}

}
