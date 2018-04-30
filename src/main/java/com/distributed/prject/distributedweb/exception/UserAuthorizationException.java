package com.distributed.prject.distributedweb.exception;

public class UserAuthorizationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public UserAuthorizationException(String message) {
        super(message);
    }
}