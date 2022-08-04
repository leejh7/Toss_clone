package com.toss.tossclone.exception;

public class NotEnoughTransactionCountException extends RuntimeException{
    public NotEnoughTransactionCountException() {
        super();
    }

    public NotEnoughTransactionCountException(String message) {
        super(message);
    }

    public NotEnoughTransactionCountException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughTransactionCountException(Throwable cause) {
        super(cause);
    }

    protected NotEnoughTransactionCountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
