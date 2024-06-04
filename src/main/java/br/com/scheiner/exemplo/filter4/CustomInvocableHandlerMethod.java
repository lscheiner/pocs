package br.com.scheiner.exemplo.filter4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.PathVariableMethodArgumentResolver;

import br.com.scheiner.exemplo.filter2.CustomRequestBodyAdviceAdapter;

public class CustomInvocableHandlerMethod extends PathVariableMethodArgumentResolver{

	@Autowired
	private CustomRequestBodyAdviceAdapter customRequestBodyAdviceAdapter;
	
	@Override
	protected void handleResolvedValue(Object arg, String name, MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest request) {
		
		this.customRequestBodyAdviceAdapter.handleEmptyBody(arg, null, parameter, null, null);
		
		super.handleResolvedValue(arg, name, parameter, mavContainer, request);
	}

	
}
