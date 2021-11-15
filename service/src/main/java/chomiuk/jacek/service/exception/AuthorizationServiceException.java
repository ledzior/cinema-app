package chomiuk.jacek.service.exception;

import chomiuk.jacek.service.service.AuthorizationService;

public class AuthorizationServiceException extends RuntimeException{
    public AuthorizationServiceException(String message){super(message);}
}
