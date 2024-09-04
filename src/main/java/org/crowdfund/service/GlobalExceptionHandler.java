package org.crowdfund.service;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.crowdfund.exceptions.InvalidRequestException;
import org.crowdfund.exceptions.NonRetryableException;
import org.crowdfund.exceptions.ProjectNotFoundException;
import org.crowdfund.exceptions.RetryableException;
import org.crowdfund.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * class for global exception handling
 * TODO modify exception handling
 */
@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    /**
     * Handle user not found exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(@NonNull final UserNotFoundException e) {
        return getRecordNotFoundResponse(e);
    }

    /**
     * Handle project not found exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler({ProjectNotFoundException.class})
    public ResponseEntity<Object> handleProjectNotFoundException(@NonNull final ProjectNotFoundException e) {
        return getRecordNotFoundResponse(e);
    }

    private ResponseEntity<Object> getRecordNotFoundResponse(@NonNull final Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    /**
     * Handle illegal argument exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(@NonNull final IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    /**
     * Handle retryable exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler({RetryableException.class})
    public ResponseEntity<Object> handleRetryableException(@NonNull final RetryableException e) {
        log.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Retry after sometime , failed with error " + e.getMessage() +
                        (e.getCause() != null ? e.getCause().getMessage() : ""));
    }

    /**
     * Handle non retryable exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler({NonRetryableException.class})
    public ResponseEntity<Object> handleNonRetryableException(@NonNull final NonRetryableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage() + (e.getCause() != null ? e.getCause().getMessage() : ""));
    }

    /**
     * Handle invalid request exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler({InvalidRequestException.class})
    public ResponseEntity<Object> handleInvalidRequestException(@NonNull final InvalidRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage() + (e.getCause() != null ? e.getCause().getMessage() : ""));
    }

//    @ExceptionHandler(value = Exception.class)
//    public ResponseEntity<Object> handleException(Exception e) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred" +
//                (e.getCause() != null ? e.getCause().getMessage() : ""));
//    }

}
