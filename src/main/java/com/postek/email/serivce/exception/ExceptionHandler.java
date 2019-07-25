package com.postek.email.serivce.exception;


import com.postek.email.model.output.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.sql.SQLException;

@ControllerAdvice
public class ExceptionHandler {

    public static final String DEFAULT_ERROR_VIEW = "error";
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    // Convert a predefined exception to an HTTP Status code
    @ResponseStatus(value = HttpStatus.GONE,
            reason = "资源不存在")  // 410
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(FileNotFoundException.class)
    public Result fileNotFound(HttpServletRequest req, FileNotFoundException ex) {
        logger.error("Request: " + req.getRequestURL(), ex);
        return Result.failWithException(ex);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public @ResponseBody
    Result handleError(HttpServletRequest req, Exception ex) {
        logger.error("Request: " + req.getRequestURL(), ex);
        return  Result.failWithException(ex);
    }



}
