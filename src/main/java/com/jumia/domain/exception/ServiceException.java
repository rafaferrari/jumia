package com.jumia.domain.exception;

/**
 * @author ferrari
 */
public class ServiceException extends Exception {

    public ServiceException() {
        super();
    }

    public ServiceException(final String message) {
        super(message);
    }

}
