package com.study.api.exception;

public class InvalidRequest extends CommonException {
   
    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidRequest () {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
