package com.study.api.exception;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public abstract class CommonException extends RuntimeException {

    public final Map<String, String> validation = new HashMap<>();
    
    public CommonException(String message) {
        super(message);
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public abstract int getStatusCode();

    public void addValidation(String fieldNmae, String Message) {
        validation.put(fieldNmae, Message);
    }
    
}
