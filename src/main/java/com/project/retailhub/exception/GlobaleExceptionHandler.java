package com.project.retailhub.exception;

import com.project.retailhub.data.dto.response.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobaleExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ResponseObject<?>> handlingRuntimeException(RuntimeException exception) {
        ResponseObject<?> responseObject = new ResponseObject<>();
        responseObject.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseObject.setMessage(exception.getMessage());
        responseObject.setSuccess(false);
        return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseObject<?>> handlingValidation(MethodArgumentNotValidException exception) {
        ResponseObject<?> responseObject = new ResponseObject<>();
        responseObject.setStatus(HttpStatus.BAD_REQUEST.value());
        responseObject.setMessage("Validation Failed: " + exception.getMessage());
        responseObject.setSuccess(false);
        return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseObject<?>> handlingGenericException(Exception exception) {
        ResponseObject<?> responseObject = new ResponseObject<>();
        responseObject.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseObject.setMessage("An unexpected error occurred: " + exception.getMessage());
        responseObject.setSuccess(false);
        return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
