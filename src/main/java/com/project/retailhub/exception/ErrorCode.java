package com.project.retailhub.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    // General
    UNCATEGORIZED_EXCEPTION(0001, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INCORRECT_USERNAME_OR_PASSWORD(0002, "Incorrect username or password", HttpStatus.UNAUTHORIZED),

    // User
    INVALID_KEY(1000, "Invalid key", HttpStatus.UNAUTHORIZED),
    INVALID_EMAIL(1001, "Invalid email", HttpStatus.BAD_REQUEST),

    // Sửa lại "User existed" thành "User already exists" để diễn đạt chính xác hơn
    USER_ALREADY_EXISTS(1002, "User already exists", HttpStatus.CONFLICT),

    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),

    // Sử dụng "User not found" cho nhất quán với các mã trạng thái HTTP khác
    USER_NOT_FOUND(1005, "User not found", HttpStatus.NOT_FOUND),

    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    USER_ID_NULL(1009, "User ID can't be null", HttpStatus.BAD_REQUEST),

    // Sửa lại HTTP status code từ "UNAUTHORIZED" thành "FORBIDDEN" để phù hợp hơn
    USER_IS_DISABLED(1010, "User has already been disabled", HttpStatus.FORBIDDEN),

    // Roles
    ROLE_NOT_FOUND(2000, "Role not found", HttpStatus.NOT_FOUND);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
