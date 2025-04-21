package com.myfirstbank.finance_kids.global.exception.code;

import org.springframework.http.HttpStatus;

public interface ExceptionCode {

    String getMessage();

    HttpStatus getHttpStatus();
}
