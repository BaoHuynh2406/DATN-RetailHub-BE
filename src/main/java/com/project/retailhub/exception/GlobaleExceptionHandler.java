package com.project.retailhub.exception;

import com.project.retailhub.data.dto.response.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;

@ControllerAdvice
@Slf4j
public class GlobaleExceptionHandler {

    private static final String MIN_ATTRIBUTE = "min";

    //Quản lý lỗi chung
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseObject<?>> handlingGenericException(Exception exception) {
        ResponseObject<?> responseObject = new ResponseObject<>();
        responseObject.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        responseObject.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObject);
    }

    //Quản lý lỗi về ứng dụng
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ResponseObject> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ResponseObject apiResponse = new ResponseObject();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    //Quảng lý lỗi về hệ thống
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ResponseObject<?>> handlingRuntimeException(RuntimeException exception) {
        ResponseObject<?> responseObject = new ResponseObject<>();
        responseObject.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseObject.setMessage(exception.getMessage());
        return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Quản lý l��i về validate dữ liệu
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseObject<?>> handlingValidation(MethodArgumentNotValidException exception) {
        ResponseObject<?> responseObject = new ResponseObject<>();
        responseObject.setCode(HttpStatus.BAD_REQUEST.value());
        responseObject.setMessage("Validation Failed: " + exception.getMessage());
        return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
    }

    //Quản lý l��i về truy cập bị từ chối
    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ResponseObject> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ResponseObject.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    //Quản lý l��i về thông tin người dùng không h��p lệ
    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<ResponseObject<?>> handlingAuthenticationException(AuthenticationException exception) {
        log.error("Authentication exception occurred: {}", exception.getMessage());
        ResponseObject<?> responseObject = new ResponseObject<>();
        responseObject.setCode(HttpStatus.UNAUTHORIZED.value());
        responseObject.setMessage("Authentication failed: " + exception.getMessage());
        return new ResponseEntity<>(responseObject, HttpStatus.UNAUTHORIZED);
    }


}
