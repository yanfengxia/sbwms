package com.beta.ms.user.config;

import brave.Tracer;
import com.beta.ms.user.exception.BaseException;
import com.beta.ms.user.notion.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@Slf4j
public class ExceptionConfig {
    @Autowired
    Tracer tracer;

    @ExceptionHandler(Exception.class)
    private ResponseEntity<Error> exceptionHandler(BaseException exception) {
        log.warn(exception.getMessage());
        String traceInfo = tracer.currentSpan().context().traceIdString()
                .concat("|")
                .concat(tracer.currentSpan().context().spanIdString());
        Error errorRO = new Error(exception.getError(), exception.getMessage(), traceInfo);
        return new ResponseEntity<>(errorRO, exception.getStatus());
    }
}
