package com.study.api.exception;

import lombok.Getter;

@Getter
public class InvalidRequest extends CommonException {
   
    private static final String MESSAGE = "잘못된 요청입니다.";

    public  String fiedlName;
    public  String message;

    public InvalidRequest () {
        super(MESSAGE);
    }

    public InvalidRequest (String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
