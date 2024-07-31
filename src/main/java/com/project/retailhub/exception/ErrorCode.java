package com.project.retailhub.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    //General
    UNCATEGORIZED_EXCEPTION(0001, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INCONRECT_USER_NAME_OR_PASSWORD(0002, "Inconrect username or password", HttpStatus.BAD_REQUEST),
    //Employees
    INVALID_KEY(1000, "Uncategorized error", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL(1001, "Email không hợp lệ", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    EMPLOYEE_ID_NULL(1009, "ID can't null", HttpStatus.BAD_REQUEST),
    //Roles
    ROLE_NOT_FOUND(2000, "Role not found", HttpStatus.NOT_FOUND),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
