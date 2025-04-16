package com.myfirstbank.finance_kids.domain.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserType {
    PARENT,
    CHILD;
}
