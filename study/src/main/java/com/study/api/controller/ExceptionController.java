package com.study.api.controller;


import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.study.api.exception.PostNotFound;
import com.study.api.response.ErrorResponse;


@ControllerAdvice
@ResponseBody
public class ExceptionController {
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {

        ErrorResponse response = ErrorResponse.builder()
            .code("400")
            .message("잘못된 요청입니다.")
            .build();
       
        for (FieldError fiedlError: e.getFieldErrors()) {
            response.addValidation(fiedlError.getField(), fiedlError.getDefaultMessage());
        }

        return response;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PostNotFound.class)
    public ErrorResponse postNotFound(PostNotFound e) {

        ErrorResponse response = ErrorResponse.builder()
            .code("404")
            .message(e.getMessage())
            .build();

        return response;
    }


}
