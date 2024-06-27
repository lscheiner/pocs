package br.com.scheiner.exemplo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ErrorMapper {

    private static final Map<Integer, Supplier<Exception>> errorMap = new HashMap<>();

    static {
        errorMap.put(1000, ExceptionTest::new);
    }

    public static Exception getExceptionForErrorCode(int errorCode) {
        Supplier<Exception> exceptionSupplier = errorMap.get(errorCode);
        if (exceptionSupplier != null) {
            return exceptionSupplier.get();
        } else {
            return new Exception("Unknown error code: " + errorCode);
        }
    }
}