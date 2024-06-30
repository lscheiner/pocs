package br.com.scheiner.exemplo;

import java.net.SocketTimeoutException;
import java.util.function.Consumer;
import java.util.function.Function;

import feign.RetryableException;

public class FunctionUtils {

    public static <T, R> R handleProcess(Function<T, R> function, T input, Consumer<T> serviceError) {
        try {
            return function.apply(input);
        } catch (RetryableException e) {
            if (e.getCause() instanceof SocketTimeoutException) {
            	serviceError.accept(input);
            }
            throw e;
        }
        catch (Exception e) {
            
            	serviceError.accept(input);
            
            throw e;
        }
    }


}