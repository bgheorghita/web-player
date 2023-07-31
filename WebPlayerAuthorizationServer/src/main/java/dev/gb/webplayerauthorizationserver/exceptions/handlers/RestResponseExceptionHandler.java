package dev.gb.webplayerauthorizationserver.exceptions.handlers;

import dev.gb.webplayerauthorizationserver.exceptions.BadRequestException;
import dev.gb.webplayerauthorizationserver.exceptions.ResourceNotFoundException;
import dev.gb.webplayerauthorizationserver.exceptions.ServiceUnavailableException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String BAD_REQUEST_MSG = "BAD REQUEST";
    public static final String NOT_FOUND_MSG = "RESOURCE NOT FOUND";
    public static final String UNEXPECTED_STATUS_CODE_MSG = "Something unexpected has happened. Please try again later.";
    public static final String UNEXPECTED_EXCEPTION_MSG = "Something went bad. Please try again later.";
    private static final String SERVICE_UNAVAILABLE_MSG = "Something unexpected happened with our services. Please try again later.";

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(Exception ex) {
        return new ResponseEntity<>(getResponseMessage(ex, HttpStatus.BAD_REQUEST), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<Object> handleServiceUnavailableException(Exception ex) {
        return new ResponseEntity<>(getResponseMessage(ex, HttpStatus.SERVICE_UNAVAILABLE), new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(Exception ex) {
        return new ResponseEntity<>(getResponseMessage(ex, HttpStatus.NOT_FOUND), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handleUnexpectedException(Exception ex) {
//        return new ResponseEntity<>(UNEXPECTED_EXCEPTION_MSG + ". DEBUG ONLY: " + Arrays.toString(ex.getStackTrace()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    private String getResponseMessage(Exception e, HttpStatus httpStatus){
        String msg = e.getMessage();
        switch (httpStatus){
            case BAD_REQUEST -> {
                return msg != null ? msg : BAD_REQUEST_MSG;
            }
            case NOT_FOUND -> {
                return msg != null ? msg : NOT_FOUND_MSG;
            }
            case SERVICE_UNAVAILABLE -> {
                return msg != null ? msg : SERVICE_UNAVAILABLE_MSG;
            }
            default -> {
                return msg != null ? msg : UNEXPECTED_STATUS_CODE_MSG;
            }
        }
    }

}
