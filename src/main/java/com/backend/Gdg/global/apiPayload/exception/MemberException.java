package com.backend.Gdg.global.apiPayload.exception;

import com.backend.Gdg.global.apiPayload.code.BaseErrorCode;

public class MemberException extends GeneralException {
    public MemberException(BaseErrorCode code) {
        super(code);
    }
}

