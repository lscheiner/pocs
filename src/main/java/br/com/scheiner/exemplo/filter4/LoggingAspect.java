package br.com.scheiner.exemplo.filter4;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerMethods() {}

    @Around("restControllerMethods()")
    public Object logResponse(ProceedingJoinPoint  joinPoint) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return joinPoint.proceed();
        }

        HttpServletResponse originalResponse = ((ServletRequestAttributes) requestAttributes).getResponse();
        if (originalResponse == null) {
            return joinPoint.proceed();
        }

        CharArrayWriterResponseWrapper responseWrapper = new CharArrayWriterResponseWrapper(originalResponse);

        Object result = joinPoint.proceed();

        int statusCode = responseWrapper.getStatus();
        String responseBody = responseWrapper.toString();

        logger.info("HTTP Status: {}", statusCode);
        logger.info("Response Body: {}", responseBody);
        logger.info("Returned Object: {}", result);

        return result;
    }

    private class CharArrayWriterResponseWrapper extends HttpServletResponseWrapper {

        private final CharArrayWriter charArrayWriter = new CharArrayWriter();
        private final HttpServletResponse originalResponse;

        public CharArrayWriterResponseWrapper(HttpServletResponse response) {
            super(response);
            this.originalResponse = response;
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            return new PrintWriter(charArrayWriter);
        }

        @Override
        public void flushBuffer() throws IOException {
            super.flushBuffer();
            charArrayWriter.flush();
        }

        @Override
        public String toString() {
            return charArrayWriter.toString();
        }

        public int getStatus() {
            return originalResponse.getStatus();
        }
    }
}
