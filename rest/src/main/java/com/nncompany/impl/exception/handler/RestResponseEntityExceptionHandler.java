package com.nncompany.impl.exception.handler;

import com.nncompany.api.dto.RequestError;
import com.nncompany.impl.exception.LoginIsAlreadyExistException;
import com.nncompany.impl.exception.NotChatMessageException;
import com.nncompany.impl.exception.NotDialogMessageException;
import com.nncompany.impl.exception.PermissionDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ BadCredentialsException.class })
    public ResponseEntity<RequestError> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new RequestError(HttpStatus.UNAUTHORIZED, e.getMessage()));
    }

    @ExceptionHandler({ NotChatMessageException.class })
    public ResponseEntity<RequestError> handleNotChatMessageException(NotChatMessageException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new RequestError(HttpStatus.NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler({ NotDialogMessageException.class })
    public ResponseEntity<RequestError> handleNotChatMessageException(NotDialogMessageException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new RequestError(HttpStatus.NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler({ EntityNotFoundException.class })
    public ResponseEntity<RequestError> handleNotChatMessageException(EntityNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new RequestError(HttpStatus.NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler({ PermissionDeniedException.class })
    public ResponseEntity<RequestError> handlePermissionDeniedException(PermissionDeniedException e) {
        String prefix = "Permission denied: ";
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new RequestError(HttpStatus.NOT_FOUND, prefix + e.getMessage()));
    }

    @ExceptionHandler({ LoginIsAlreadyExistException.class })
    public ResponseEntity<RequestError> handleLoginIsAlreadyExistException(LoginIsAlreadyExistException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new RequestError(HttpStatus.CONFLICT, e.getMessage()));
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<RequestError> handleUncaughtExceptions(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new RequestError(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage()));
    }
}
