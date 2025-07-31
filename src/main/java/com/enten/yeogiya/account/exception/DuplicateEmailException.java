package com.enten.yeogiya.account.exception;

public class DuplicateEmailException extends RuntimeException{


    public DuplicateEmailException(String message) {
        super(message);
    }

    public DuplicateEmailException() {
        super("이메일 중복 입니다.");
    }
}
