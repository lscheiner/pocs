package br.com.scheiner.exemplo.filter2;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import jakarta.servlet.http.HttpServletRequest;


//Eles são invocados apenas quando há um corpo de requisição a ser processado, o que ocorre com métodos HTTP como POST, PUT, PATCH
@RestControllerAdvice
public class CustomRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {

    private final List<String> allowedUrls = Arrays.asList("/mensagem/**", "/produtos/**");
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private final List<String> exclusionList = Arrays.asList("host");
    
    
	@Override
	public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
		
		HttpServletRequest request =  ServletRequestAttributes.class.cast(RequestContextHolder.getRequestAttributes()).getRequest();
        
		String urlPath = request.getRequestURI();

		if (Objects.isNull(this.allowedUrls) || this.allowedUrls.isEmpty()) {
			return true;
		}

        return allowedUrls.stream().anyMatch(allowedUrl -> this.antPathMatcher.match(allowedUrl, urlPath));
	}

	@Override
	public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

		List<Header> headerList = inputMessage.getHeaders().entrySet().stream()
				.filter(entry -> !exclusionList.contains(entry.getKey()))
				.flatMap(entry -> entry.getValue().stream().map(value -> new Header(entry.getKey(), value)))
				.toList();

		System.out.println(headerList);

		System.out.println(body);

		return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
	}

	//quando o request é null , 
	@Override
	public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
			Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
		// TODO Auto-generated method stub
		return super.handleEmptyBody(body, inputMessage, parameter, targetType, converterType);
	}
	
	
}
