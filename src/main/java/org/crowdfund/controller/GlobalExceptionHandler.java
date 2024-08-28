package org.crowdfund.controller;

import lombok.NonNull;
import org.crowdfund.exceptions.NonRetryableException;
import org.crowdfund.exceptions.ProjectNotFoundException;
import org.crowdfund.exceptions.RetryableException;
import org.crowdfund.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import software.amazon.awssdk.services.dynamodb.model.ConditionalCheckFailedException;



/**
 * class for global exception handling
 * TODO modify exception handling
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(@NonNull final UserNotFoundException e) {
        return getRecordNotFoundResponse(e);
    }

    @ExceptionHandler({ProjectNotFoundException.class})
    public ResponseEntity<Object> handleProjectNotFoundException(@NonNull final ProjectNotFoundException e) {
        return getRecordNotFoundResponse(e);
    }


    private ResponseEntity<Object> getRecordNotFoundResponse(@NonNull final Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(@NonNull final IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler({ConditionalCheckFailedException.class})
    public ResponseEntity<Object> handleConditionalCheckFailedException(@NonNull final ConditionalCheckFailedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler({RetryableException.class})
    public ResponseEntity<Object> handleRetryableException(@NonNull final RetryableException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }

    @ExceptionHandler({NonRetryableException.class})
    public ResponseEntity<Object> handleNonRetryableException(@NonNull final NonRetryableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

}
