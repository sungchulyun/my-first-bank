package com.myfirstbank.finance_kids.global.exception;

import com.myfirstbank.finance_kids.global.exception.code.ExceptionCode;
import lombok.Getter;

@Getter
public abstract class AbstractCustomException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    protected AbstractCustomException(ExceptionCode exceptionCode){
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    @Override
    public String getMessage() {
        return exceptionCode.getMessage();
    }
}
