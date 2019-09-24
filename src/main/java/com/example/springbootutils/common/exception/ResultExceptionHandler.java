package com.example.springbootutils.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResultExceptionHandler {
    @ExceptionHandler(value = ResultException.class)
    @ResponseBody
    public NResult resultExceptionHandler(HttpServletRequest req, Exception ex) throws Exception {
        ResultException exception;
        if (ex instanceof ResultException) {
            exception = (ResultException) ex;
        }else {
            exception = (ResultException)ex.getCause();
        }
        NResult object = new NResult();

        object.setCode(exception.getCode());
        object.setMessage(exception.getMessage());
        object.setData(null);

        return object;
    }
}
