package com.myfirstbank.finance_kids.global.exception;

import com.myfirstbank.finance_kids.global.exception.code.ExceptionCode;
import lombok.Getter;

@Getter
public class CustomException extends AbstractCustomException{
    public CustomException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
