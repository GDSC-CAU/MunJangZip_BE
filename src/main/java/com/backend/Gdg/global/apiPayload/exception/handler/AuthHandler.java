package com.backend.Gdg.global.apiPayload.exception.handler;

import jakarta.security.auth.message.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

import static com.backend.Gdg.global.apiPayload.code.status.ErrorStatus.AUTH_INVALID_TOKEN;
import static com.backend.Gdg.global.apiPayload.code.status.ErrorStatus.MEMBER_NOT_FOUND;

@RestControllerAdvice
public class AuthHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> handleAuthException(AuthException ex) {
        HttpStatus status;

        if (ex.equals(AUTH_INVALID_TOKEN)) {
            status = HttpStatus.UNAUTHORIZED; // 401
        } else if (ex.equals(MEMBER_NOT_FOUND)) {
            status = HttpStatus.NOT_FOUND; // 404
        } else {
            status = HttpStatus.BAD_REQUEST; // 400
        }

        return ResponseEntity.status(status)
                .body(Map.of("error", ex.getMessage()));
    }
}

