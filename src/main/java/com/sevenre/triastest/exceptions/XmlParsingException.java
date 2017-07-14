package com.sevenre.triastest.exceptions;

/**
 * Created by nikhilesh on 14/07/17.
 */
public class XmlParsingException extends RuntimeException {
    public XmlParsingException() {
        super();
    }

    public XmlParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlParsingException(String message) {
        super(message);
    }

    public XmlParsingException(Throwable cause) {
        super(cause);
    }
}
