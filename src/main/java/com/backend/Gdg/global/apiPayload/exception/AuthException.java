package com.backend.Gdg.global.apiPayload.exception;


import com.backend.Gdg.global.apiPayload.code.BaseErrorCode;

public class AuthException extends GeneralException {

    public AuthException(BaseErrorCode code) {
        super(code);
    }
}