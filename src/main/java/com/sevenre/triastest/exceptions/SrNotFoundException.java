package com.sevenre.triastest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by nikhilesh on 14/07/17.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class SrNotFoundException extends RuntimeException {
    public SrNotFoundException() {
        super();
    }

    public SrNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SrNotFoundException(String message) {
        super(message);
    }

    public SrNotFoundException(Throwable cause) {
        super(cause);
    }
}