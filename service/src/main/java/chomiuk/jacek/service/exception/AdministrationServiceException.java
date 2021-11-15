package chomiuk.jacek.service.exception;

import chomiuk.jacek.service.service.AdministrationService;

public class AdministrationServiceException extends RuntimeException{
    public AdministrationServiceException(String message){super(message);}
}
