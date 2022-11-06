package com.ets.fileupload.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiExceptionModel extends RuntimeException  {
    @Getter
    private final String message;
    @Getter
    private final Throwable throwable;
    @Getter
    private final HttpStatus httpStatus;
    @Getter
    private final ZonedDateTime timeStamp;

    public ApiExceptionModel(String message, Throwable throwable, HttpStatus httpStatus, ZonedDateTime timeStamp) {
        this.message = message;
        this.throwable = throwable;
        this.httpStatus = httpStatus;
        this.timeStamp = timeStamp;
    }
}
