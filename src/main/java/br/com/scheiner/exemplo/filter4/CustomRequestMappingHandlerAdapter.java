package br.com.scheiner.exemplo.filter4;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

public class CustomRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter{

	@Override
    public void afterPropertiesSet() {

		super.afterPropertiesSet();
		
		this.getMessageConverters().add(new MappingJackson2HttpMessageConverter ());
	    List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>();
	    argumentResolvers.add(new CustomInvocableHandlerMethod());
	    argumentResolvers.addAll(this.getArgumentResolvers());
	    this.setArgumentResolvers(argumentResolvers);
    }
	
	/*
	 * 
	 * HandlerMethodArgumentResolverComposite -- linha 117
	   InvocableHandlerMethod  -  linha 207
       RequestResponseBodyMethodProcessor -- linha 159
       readWithMessageConverters -- linha 186
	 * 
	 * */
}
