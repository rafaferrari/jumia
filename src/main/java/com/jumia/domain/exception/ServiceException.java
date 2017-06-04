package com.jumia.domain.exception;

/**
 * Exception to use in Service Layer for Business Errors.
 *
 * @author rafael.ferrari
 */
public class ServiceException extends Exception {

    public ServiceException() {
        super();
    }

    public ServiceException(final String message) {
        super(message);
    }

}
