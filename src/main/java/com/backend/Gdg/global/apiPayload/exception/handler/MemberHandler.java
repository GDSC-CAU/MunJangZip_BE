package com.backend.Gdg.global.apiPayload.exception.handler;

import com.backend.Gdg.global.apiPayload.code.BaseErrorCode;
import com.backend.Gdg.global.apiPayload.exception.GeneralException;

public class MemberHandler extends GeneralException {
    public MemberHandler(BaseErrorCode baseErrorCode){
        super(baseErrorCode);
    }
}
