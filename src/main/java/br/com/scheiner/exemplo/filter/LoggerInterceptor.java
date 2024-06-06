package br.com.scheiner.exemplo.filter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoggerInterceptor implements HandlerInterceptor {
//https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/util/ContentCachingRequestWrapper.html
	
	static ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
			throws Exception {

		if (handler instanceof HandlerMethod handlerMethod) {
			
			for (MethodParameter methodParameter :  handlerMethod.getMethodParameters()) {
				
				if (methodParameter.hasParameterAnnotation(RequestBody.class)) {
					
					Type returnType = handlerMethod.getMethod().getGenericParameterTypes()[0];

					if (request instanceof CachedHttpServletRequestWrapper requestWrapper) {
						
						Object obj  = getObject(returnType, requestWrapper.getContentAsByteArray());
						
						if (Objects.nonNull(obj))
							System.out.println("request ["+mapper.writeValueAsString(obj)+"]");
						
					}
					break;
				}
			}
        } 
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		if (handler instanceof HandlerMethod handlerMethod
				&& response instanceof ContentCachingResponseWrapper contentCachingResponseWrapper) {
			
			Type returnType = handlerMethod.getMethod().getGenericParameterTypes()[0];

			Object obj  = getObject(returnType, contentCachingResponseWrapper.getContentAsByteArray());
			
			if (Objects.nonNull(obj))
				System.out.println("response [" + mapper.writeValueAsString(obj) + "]");
			
		}

	}
	
	
	private Object getObject(Type type, byte[] json) {

		try {
			if (type instanceof ParameterizedType parameterizedType
					&& ((ParameterizedType) type).getRawType().getTypeName().equals(ResponseEntity.class.getName())) {
				Type[] typeArguments = parameterizedType.getActualTypeArguments();
				if (typeArguments.length > 0) {
					type = typeArguments[0];
				}
			}

			JavaType javaType = mapper.getTypeFactory().constructType(type);
			return mapper.readValue(json, javaType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



	
	/*
	 *
	 * 		Class<?> clazz = handlerMethod.getMethod().getDeclaringClass();
			Class<?>[] param = handlerMethod.getMethod().getParameterTypes();
			Method method = clazz.getMethod(handlerMethod.getMethod().getName(), param);
			Type returnType = method.getGenericReturnType();
	 * 
	 */
}
	
