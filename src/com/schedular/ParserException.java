package com.schedular;

/**
 * Exception class specific for handling exception during parsing .
 */
public class ParserException extends Exception {

    private static final long serialVersionUID = 1L;

    public ParserException(String message) {
        super(message);
    }
}
