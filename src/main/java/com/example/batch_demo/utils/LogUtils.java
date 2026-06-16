package com.example.batch_demo.utils;

public class LogUtils {

    private LogUtils() {
    }



    /**
     * Returns the deepest cause so logs stay readable and classification is more reliable.
     */
    public static Throwable getRootCause(Throwable throwable) {
        Throwable current = throwable;

        while (current.getCause() != null && current.getCause() != current) {
            current = current.getCause();
        }

        return current;
    }
}
